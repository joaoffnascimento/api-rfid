package br.edu.ifs.rfid.apirfid.shared;

import java.util.ArrayList;
import java.util.List;

import org.llrp.ltk.generated.enumerations.AISpecStopTriggerType;
import org.llrp.ltk.generated.enumerations.AccessReportTriggerType;
import org.llrp.ltk.generated.enumerations.AccessSpecState;
import org.llrp.ltk.generated.enumerations.AccessSpecStopTriggerType;
import org.llrp.ltk.generated.enumerations.AirProtocols;
import org.llrp.ltk.generated.enumerations.ROReportTriggerType;
import org.llrp.ltk.generated.enumerations.ROSpecStartTriggerType;
import org.llrp.ltk.generated.enumerations.ROSpecState;
import org.llrp.ltk.generated.enumerations.ROSpecStopTriggerType;
import org.llrp.ltk.generated.enumerations.StatusCode;
import org.llrp.ltk.generated.interfaces.AccessCommandOpSpec;
import org.llrp.ltk.generated.messages.ADD_ACCESSSPEC;
import org.llrp.ltk.generated.messages.ADD_ACCESSSPEC_RESPONSE;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.llrp.ltk.generated.messages.ADD_ROSPEC_RESPONSE;
import org.llrp.ltk.generated.messages.DELETE_ACCESSSPEC;
import org.llrp.ltk.generated.messages.DELETE_ACCESSSPEC_RESPONSE;
import org.llrp.ltk.generated.messages.DELETE_ROSPEC;
import org.llrp.ltk.generated.messages.DELETE_ROSPEC_RESPONSE;
import org.llrp.ltk.generated.messages.ENABLE_ACCESSSPEC;
import org.llrp.ltk.generated.messages.ENABLE_ACCESSSPEC_RESPONSE;
import org.llrp.ltk.generated.messages.ENABLE_ROSPEC;
import org.llrp.ltk.generated.messages.ENABLE_ROSPEC_RESPONSE;
import org.llrp.ltk.generated.messages.RO_ACCESS_REPORT;
import org.llrp.ltk.generated.messages.START_ROSPEC;
import org.llrp.ltk.generated.messages.START_ROSPEC_RESPONSE;
import org.llrp.ltk.generated.parameters.AISpec;
import org.llrp.ltk.generated.parameters.AISpecStopTrigger;
import org.llrp.ltk.generated.parameters.AccessCommand;
import org.llrp.ltk.generated.parameters.AccessReportSpec;
import org.llrp.ltk.generated.parameters.AccessSpec;
import org.llrp.ltk.generated.parameters.AccessSpecStopTrigger;
import org.llrp.ltk.generated.parameters.C1G2Read;
import org.llrp.ltk.generated.parameters.C1G2TagSpec;
import org.llrp.ltk.generated.parameters.C1G2TargetTag;
import org.llrp.ltk.generated.parameters.InventoryParameterSpec;
import org.llrp.ltk.generated.parameters.PeriodicTriggerValue;
import org.llrp.ltk.generated.parameters.ROBoundarySpec;
import org.llrp.ltk.generated.parameters.ROReportSpec;
import org.llrp.ltk.generated.parameters.ROSpec;
import org.llrp.ltk.generated.parameters.ROSpecStartTrigger;
import org.llrp.ltk.generated.parameters.ROSpecStopTrigger;
import org.llrp.ltk.generated.parameters.TagReportContentSelector;
import org.llrp.ltk.generated.parameters.TagReportData;
import org.llrp.ltk.net.LLRPConnection;
import org.llrp.ltk.net.LLRPConnectionAttemptFailedException;
import org.llrp.ltk.net.LLRPConnector;
import org.llrp.ltk.net.LLRPEndpoint;
import org.llrp.ltk.types.Bit;
import org.llrp.ltk.types.BitArray_HEX;
import org.llrp.ltk.types.LLRPMessage;
import org.llrp.ltk.types.TwoBitField;
import org.llrp.ltk.types.UnsignedByte;
import org.llrp.ltk.types.UnsignedInteger;
import org.llrp.ltk.types.UnsignedShort;
import org.llrp.ltk.types.UnsignedShortArray;

public class RfidMiddleware implements LLRPEndpoint {

	private LLRPConnection reader;

	private static final int READ_ACCESSSPEC_ID = 444;
	private static final int READ_OPSPEC_ID = 1212;
	private static final int TIMEOUT_MS = 10000;
	private static final int ROSPEC_ID = 123;

	public List<String> listaLeiturasEPC = new ArrayList<String>();

	int count = 0;

	// Constroi o ROSpec.
	// Um ROSpec inicia e para os triggers
	// tag report fields, antennas, etc.
	public ROSpec buildROSpec() { // construção de um rospec
		System.out.println("Construindo o ROSpec.");
		// Cria o Reader Operation Spec (ROSpec).
		ROSpec roSpec = new ROSpec();

		roSpec.setPriority(new UnsignedByte(0));
		roSpec.setCurrentState(new ROSpecState(ROSpecState.Disabled));// estado inicial de desativado
		roSpec.setROSpecID(new UnsignedInteger(ROSPEC_ID));// ROSPEC_ID = 123 atributo estatico definido la emcima,pode
															// ser qualquer numero

		// Configura o ROBoundarySpec
		// Isto define o gatilho de inicio e de parada
		ROBoundarySpec roBoundarySpec = new ROBoundarySpec();

		// Define o gatilho inicial como NULL.
		// Isto significa que o ROSpec vai come�ar assim que ele estiver habilitado.
		ROSpecStartTrigger startTrig = new ROSpecStartTrigger();
		startTrig.setROSpecStartTriggerType(new ROSpecStartTriggerType(ROSpecStartTriggerType.Periodic));// talvez mudar
																											// para
																											// imediatamente
																											// para ler
																											// assim que
																											// o rospec
																											// for
																											// ativado
		PeriodicTriggerValue periodicTrigger = new PeriodicTriggerValue();
		// Para evitar v�rias leituras pela leitora,
		// 5000 e 5000 o padrao
		// acredito que é tempo inicial de leitura
		// de quanto em quanto tempo vai ler
		periodicTrigger.setPeriod(new UnsignedInteger(8000));// � definido nestes dois metodos durante quanto tempo é
																// realizado leituras
		periodicTrigger.setOffset(new UnsignedInteger(8000));// e de quanto em quanto tempo deve realizar leitura.
		startTrig.setPeriodicTriggerValue(periodicTrigger);

		roBoundarySpec.setROSpecStartTrigger(startTrig);

		// Define que o gatilho de parada � nulo. Isto significa que o ROSpec
		// vai continuar executando at� uma mensagem STOP_ROSPEC for enviada.
		ROSpecStopTrigger stopTrig = new ROSpecStopTrigger();
		stopTrig.setDurationTriggerValue(new UnsignedInteger(5000));// padrao e 2000//interromper a leitura por 10
																	// segundos
		stopTrig.setROSpecStopTriggerType(new ROSpecStopTriggerType(ROSpecStopTriggerType.Duration));// parada

		roBoundarySpec.setROSpecStopTrigger(stopTrig);

		roSpec.setROBoundarySpec(roBoundarySpec);

		// Adiciona uma Antenna Inventory Spec (AISpec).
		AISpec aispec = new AISpec();

		// Defina o gatilho de parada do AI como nulo. Isto significa que
		// o AI especificado ser� executado at� a parada do ROSpec.
		AISpecStopTrigger aiStopTrigger = new AISpecStopTrigger();
		aiStopTrigger.setAISpecStopTriggerType(new AISpecStopTriggerType(AISpecStopTriggerType.Null));
		aiStopTrigger.setDurationTrigger(new UnsignedInteger(0));
		aispec.setAISpecStopTrigger(aiStopTrigger);

		// Selecione as portas das antena que deseja usar.
		// Colocando zero na propriedade, significa que quer usar todas as portas de
		// Antenas
		UnsignedShortArray antennaIDs = new UnsignedShortArray();
		antennaIDs.add(new UnsignedShort(0));
		aispec.setAntennaIDs(antennaIDs);

		// Diz ao leitor que iremos ler tags Gen2
		InventoryParameterSpec inventoryParam = new InventoryParameterSpec();
		inventoryParam.setProtocolID(new AirProtocols(AirProtocols.EPCGlobalClass1Gen2));
		inventoryParam.setInventoryParameterSpecID(new UnsignedShort(1));
		aispec.addToInventoryParameterSpecList(inventoryParam);

		roSpec.addToSpecParameterList(aispec);

		// Especifica o tipo de relatorios que queremos
		// receber e quando queremos receb�-los
		ROReportSpec roReportSpec = new ROReportSpec();
		// Receber um relatorio cada vez que uma tag for lida.
		roReportSpec.setROReportTrigger(new ROReportTriggerType(ROReportTriggerType.Upon_N_Tags_Or_End_Of_ROSpec));
		roReportSpec.setN(new UnsignedShort(1));
		TagReportContentSelector reportContent = new TagReportContentSelector();
		// Seleciona os campos que queremos no relatorio
		reportContent.setEnableAccessSpecID(new Bit(0));
		reportContent.setEnableAntennaID(new Bit(0));
		reportContent.setEnableChannelIndex(new Bit(0));
		reportContent.setEnableFirstSeenTimestamp(new Bit(0));
		reportContent.setEnableInventoryParameterSpecID(new Bit(0));
		reportContent.setEnableLastSeenTimestamp(new Bit(1));
		reportContent.setEnablePeakRSSI(new Bit(0));
		reportContent.setEnableROSpecID(new Bit(0));
		reportContent.setEnableSpecIndex(new Bit(0));
		reportContent.setEnableTagSeenCount(new Bit(0));
		roReportSpec.setTagReportContentSelector(reportContent);
		roSpec.setROReportSpec(roReportSpec);

		return roSpec;
	}

	// Adiciona um ROSpec ao leitor
	public void addROSpec() {
		ADD_ROSPEC_RESPONSE response;

		ROSpec roSpec = buildROSpec(); // construir rospec
		System.out.println("Adicionando o ROSpec.");
		try {
			ADD_ROSPEC roSpecMsg = new ADD_ROSPEC();
			roSpecMsg.setROSpec(roSpec);
			response = (ADD_ROSPEC_RESPONSE) reader.transact(roSpecMsg, TIMEOUT_MS);// enviando a mensagem para o leitor
			System.out.println(response.toXMLString());

			// Incluir na Interface
			// textoTelaPrincipal.append(response.toXMLString() + "\n");
			System.out.println(response.toXMLString() + "\n");

			// Verifica se foi adicionado com sucesso o ROSpec ao leitor
			StatusCode status = response.getLLRPStatus().getStatusCode();
			if (status.equals(new StatusCode("M_Success"))) {
				System.out.println("Add ROSpec adicionado com Sucesso.");
				// textoTelaPrincipal.append("Add ROSpec com Sucesso." + "\n");
			} else {
				System.out.println("Erro ao adicionar o ROSpec.");
				System.exit(1);
			}
		} catch (Exception e) {
			System.out.println("Erro ao adicionar o ROSpec.");
			e.printStackTrace();
		}
	}

	// Habilita o ROSpec.
	public void enableROSpec() {
		ENABLE_ROSPEC_RESPONSE response;

		System.out.println("Habilitando o ROSpec.");
		ENABLE_ROSPEC enable = new ENABLE_ROSPEC();
		enable.setROSpecID(new UnsignedInteger(ROSPEC_ID));
		try {
			response = (ENABLE_ROSPEC_RESPONSE) reader.transact(enable, TIMEOUT_MS);
			System.out.println(response.toXMLString());
			// textoTelaPrincipal.append(response.toXMLString() + "\n");
			// textoTelaPrincipal.append("Enable Rospec habilitado com Sucesso!" + "\n");
			System.out.println("Enable Rospec habilitado com Sucesso!" + "\n");
		} catch (Exception e) {
			System.out.println("Erro ao habilitar o Enable Rospec.");
			// textoTelaPrincipal.append("Erro ao habilitar o ROSpec." + "\n");
			e.printStackTrace();
		}
	}

	// Inicia o ROSpec.
	public void startROSpec() {
		START_ROSPEC_RESPONSE response;
		System.out.println("Inciando o ROSpec.");

		START_ROSPEC start = new START_ROSPEC();
		start.setROSpecID(new UnsignedInteger(ROSPEC_ID));
		try {
			response = (START_ROSPEC_RESPONSE) reader.transact(start, TIMEOUT_MS);
			System.out.println(response.toXMLString() + "\n");
			// textoTelaPrincipal.append(response.toXMLString() + "\n");
			System.out.println("StartROSpec Iniciado com Sucesso!." + "\n");
			// textoTelaPrincipal.append("StartROSpec Iniciado com Sucesso!." + "\n");
			System.out.println("Leitor Iniciado! \n");
		} catch (Exception e) {
			// textoTelaPrincipal.append("Erro ao iniciar o StartROSpec." + "\n");
			System.out.println("Erro ao iniciar o StartROSpec." + "\n");
			e.printStackTrace();
		}
	}

	// Deleta todos os ROSpecs da leitora.
	public void deleteROSpecs() {
		DELETE_ROSPEC_RESPONSE response;

		System.out.println("Deletando todos os ROSpecs.");
		DELETE_ROSPEC del = new DELETE_ROSPEC();
		// Use zero como ID do ROSpec
		// Isso significa deletar todos os ROSpec
		del.setROSpecID(new UnsignedInteger(0));
		try {
			response = (DELETE_ROSPEC_RESPONSE) reader.transact(del, TIMEOUT_MS);
			System.out.println(response.toXMLString());
			// textoTelaPrincipal.append(response.toXMLString() + "\n");
		} catch (Exception e) {
			System.out.println("Erro ao deletar os ROSpec.");
			// textoTelaPrincipal.append("Erro ao deletar os ROSpec." + "\n");
			e.printStackTrace();
		}
	}

	// Esta fun��o � chamada de forma assincrona
	// quando ocorre um erro
	public void errorOccured(String s) {
		System.out.println("An error occurred: " + s);
	}

	// Conecta a leitora
	public void connect(String hostname) {

		// cria novo objeto do tipo LLRP connector passando(LLRP endpoint e um ip)
		reader = new LLRPConnector(this, hostname);
		// textoTelaPrincipal.append("Conectar no IP: " + ((LLRPConnector)
		// reader).getHost() + "\n");
		System.out.println("Conectar no IP: " + ((LLRPConnector) reader).getHost());
		// textoTelaPrincipal.append("Porta: " + ((LLRPConnector) reader).getPort() +
		// "\n");
		System.out.println("Porta: " + ((LLRPConnector) reader).getPort());

		// Tenta conectar a leitora
		try {
			// ((LLRPConnector) reader).getHandler().getConnection().reconnect();
			((LLRPConnector) reader).connect();
			// caso nao consiga conectar reconectar
			// //JOptionPane.showMessageDialog(null,"a conexao foi reonectada: "+
			// ((LLRPConnector) reader).reconnect());
			// textoTelaPrincipal.append("Conectado com Sucesso! \n \n");
			System.out.println("Conectado com Sucesso! \n");

		} catch (LLRPConnectionAttemptFailedException e1) {
			// textoTelaPrincipal.append("Não foi possivel conectar-se ao leitor \n \n");
			System.out.println("Não foi possivel conectar-se ao leitor \n");
			e1.printStackTrace();
			System.exit(1);
		}
	}

	// Disconecta da leitora
	public void disconnect() {
		try {
			((LLRPConnector) reader).disconnect();
			// textoTelaPrincipal.append("Desconectado com Sucesso!");
			System.out.println("Desconectado com Sucesso! \n");
		} catch (Exception e2) {
			// textoTelaPrincipal.append("Não foi possível se desconectar do leitor");
			System.out.println("Não foi possível se desconectar do leitor");
			e2.printStackTrace();
		}
	}

	// Conectar ao leitor, configura o ROSpec
	// e o executa
	public void run(String hostname) {
		// this.textoTelaPrincipal.append("1 - Conectando-se ao Leitor" + "\n");
		System.out.println("1 - Conectando-se ao Leitor" + "\n");
		connect(hostname); // ok
		// this.textoTelaPrincipal.append("2 - Deletando Accespecs" + "\n");
		System.out.println("2 - Deletando Accespecs" + "\n");
		deleteAccessSpecs(); // ok
		// this.textoTelaPrincipal.append("3 - Deletando ROSpecs" + "\n");
		System.out.println("3 - Deletando ROSpecs" + "\n");
		deleteROSpecs(); // ok
		// this.textoTelaPrincipal.append("4 - Adicionando ROSpecs" + "\n");
		System.out.println("4 - Adicionando ROSpecs" + "\n");
		addROSpec(); // ok
		// this.textoTelaPrincipal.append("5 - Adicionando Accespecs" + "\n");
		System.out.println("5 - Adicionando Accespecs" + "\n");
		addAccessSpec(READ_ACCESSSPEC_ID);
		// this.textoTelaPrincipal.append("6 - Ativando Accespecs" + "\n");
		System.out.println("6 - Ativando Accespecs" + "\n");
		enableAccessSpec(READ_ACCESSSPEC_ID);
		// this.textoTelaPrincipal.append("7 - Ativando ROSPEC" + "\n");
		System.out.println("7 - Ativando ROSPEC" + "\n");
		enableROSpec(); // ok
		// this.textoTelaPrincipal.append("8 - Iniciando ROSPec" + "\n");
		System.out.println("8 - Iniciando ROSPec" + "\n");
		startROSpec(); // ok
		System.out.println(
				"---------------------------------------------------------------------------------------------" + "\n");
		// this.textoTelaPrincipal.append("---------------------------------------------------------------------------------------------"
		// + "\n");
	}

	// Deleta todos os ROSpecs
	// e desliga o leitor
	public void stop() {
		// esvaziando as configurações
		deleteAccessSpecs();
		deleteROSpecs();
		disconnect();
	}

	// Habilita o AccessSpec
	public void enableAccessSpec(int accessSpecID) {
		ENABLE_ACCESSSPEC_RESPONSE response;

		System.out.println("Habilitando o AccessSpec.");
		ENABLE_ACCESSSPEC enable = new ENABLE_ACCESSSPEC();
		enable.setAccessSpecID(new UnsignedInteger(accessSpecID));
		try {
			response = (ENABLE_ACCESSSPEC_RESPONSE) reader.transact(enable, TIMEOUT_MS);
			System.out.println(response.toXMLString());
			// textoTelaPrincipal.append(response.toXMLString() + "\n");
			System.out.println("o enableAccessSpec foi habilitado com sucesso." + "\n");
			// textoTelaPrincipal.append("o enableAccessSpec foi habilitado com sucesso." +
			// "\n");
		} catch (Exception e) {
			System.out.println("Erro ao habilitar o enableAccessSpec.");
			// textoTelaPrincipal.append("Erro ao habilitar o enableAccessSpec." + "\n");
			e.printStackTrace();
		}
	}

	// Exclui todos os AccessSpecs do leitor
	public void deleteAccessSpecs() {
		// textoTelaPrincipal.append("DELETAR ACESSPEC" + "\n");
		System.out.println("DELETAR ACESSPEC" + "\n");
		DELETE_ACCESSSPEC_RESPONSE response;

		System.out.println("Deletando todos os AccessSpecs.");
		DELETE_ACCESSSPEC del = new DELETE_ACCESSSPEC();
		// Use zero como o ID do ROSpec.
		// Isso significa eliminar todas os AccessSpecs.
		del.setAccessSpecID(new UnsignedInteger(0));
		try {
			response = (DELETE_ACCESSSPEC_RESPONSE) reader.transact(del, TIMEOUT_MS);
			System.out.println(response.toXMLString());
			// textoTelaPrincipal.append(response.toXMLString() + "\n");
		} catch (Exception e) {
			System.out.println("Erro ao deletar o AccessSpec.");
			// textoTelaPrincipal.append("Erro ao deletar o AccessSpec." + "\n");
			e.printStackTrace();
		}
	}

	// Adicionar o AccessSpec para o leitor.
	public void addAccessSpec(int accessSpecID) {
		ADD_ACCESSSPEC_RESPONSE response;

		AccessSpec accessSpec = buildAccessSpec(accessSpecID);
		System.out.println("Adicionando o AccessSpec.");
		try {
			ADD_ACCESSSPEC accessSpecMsg = new ADD_ACCESSSPEC();
			accessSpecMsg.setAccessSpec(accessSpec);
			response = (ADD_ACCESSSPEC_RESPONSE) reader.transact(accessSpecMsg, TIMEOUT_MS);
			System.out.println(response.toXMLString());
			// textoTelaPrincipal.append(response.toXMLString() + "\n");

			// Check if the we successfully added the AccessSpec.
			StatusCode status = response.getLLRPStatus().getStatusCode();
			if (status.equals(new StatusCode("M_Success"))) {
				System.out.println("Sucesso ao adicionar o AccessSpec.");
				// textoTelaPrincipal.append("Sucesso ao adicionar o AccessSpec." + "\n");
			} else {
				System.out.println("Erro ao adicionar o AccessSpec.");
				// textoTelaPrincipal.append("Erro ao adicionar o AccessSpec." + "\n");
				System.exit(1);
			}
		} catch (Exception e) {
			System.out.println("Erro ao adicionar o AccessSpec.");
			// textoTelaPrincipal.append("Erro ao adicionar o AccessSpec." + "\n");
			e.printStackTrace();
		}
	}

	// Criar um OpSpec que l� a partir da mem�ria de usu�rio (user memory)
	public C1G2Read buildReadOpSpec() {
		// Cria um novo OpSpec
		// Isto especifica que a opera��o que deseja executar nas
		// Tags que correspondem �s especifica��es.
		// Neste caso, queremos ler a tag.
		C1G2Read opSpec = new C1G2Read();
		// Definir o OpSpecID a um n�mero �nico.
		opSpec.setOpSpecID(new UnsignedShort(READ_OPSPEC_ID));
		opSpec.setAccessPassword(new UnsignedInteger(0));
		// Neste caso, vamos ler a partir da mem�ria do usu�rio (banco 3).
		TwoBitField opMemBank = new TwoBitField();
		// Define os bits 0 e 1 (banco 3 em bin�rio).
		// opMemBank.set(0);
		// opMemBank.set(1);
		opSpec.setMB(opMemBank);
		// Vamos ler a partir da base deste banco de mem�ria (0x00).
		opSpec.setWordPointer(new UnsignedShort(0x20));
		// Leia 2 palavras
		opSpec.setWordCount(new UnsignedShort(32));

		return opSpec;
	}

	// Criar um OpSpec que escreve na mem�ria de usu�rio (user memory)
	// Cria o AccessSpec.
	// Ele ir� conter nossos dois OpSpecs (ler e escrever).
	public AccessSpec buildAccessSpec(int accessSpecID) {
		System.out.println("Construindo o AccessSpec.");

		AccessSpec accessSpec = new AccessSpec();

		accessSpec.setAccessSpecID(new UnsignedInteger(accessSpecID));

		// Define o ROSpec ID como zero
		// Isto significa que o AccessSpec ser�o aplicadas a todos ROSpecs.
		accessSpec.setROSpecID(new UnsignedInteger(0));
		// Antena ID zero significa que � para todas as antenas.
		accessSpec.setAntennaID(new UnsignedShort(0));
		accessSpec.setProtocolID(new AirProtocols(AirProtocols.EPCGlobalClass1Gen2));
		// AccessSpecs deve estar desativado quando voc� adicion�-los.
		accessSpec.setCurrentState(new AccessSpecState(AccessSpecState.Disabled));
		AccessSpecStopTrigger stopTrigger = new AccessSpecStopTrigger();
		// Parar ap�s a opera��o foi executada um certo n�mero de vezes.
		// Esse n�mero � especificado pelo par�metro Operation_Count.
		stopTrigger.setAccessSpecStopTrigger(new AccessSpecStopTriggerType(AccessSpecStopTriggerType.Operation_Count));
		// OperationCountValue indicam o n�mero de vezes que esse Spec �
		// executado antes de ser exclu�do. Se definido como 0, equivale
		// que nenhum gatilho de parada definido.
		stopTrigger.setOperationCountValue(new UnsignedShort(0));
		accessSpec.setAccessSpecStopTrigger(stopTrigger);

		// Criar um novo AccessCommand.
		// Usamos isso para especificar quais as tags que queremos utilizar.
		AccessCommand accessCommand = new AccessCommand();

		// Criar uma nova especifica��o de tag.
		C1G2TagSpec tagSpec = new C1G2TagSpec();
		C1G2TargetTag targetTag = new C1G2TargetTag();
		targetTag.setMatch(new Bit(1));
		// Queremos verificar a mem�ria do banco 1 (o banco de mem�ria EPC).
		TwoBitField memBank = new TwoBitField();
		// Limpar o bit 0 e bit 1 (banco 1 em bin�rio).
		// memBank.clear(0);
		// memBank.set(0);
		targetTag.setMB(memBank);
		// Os dados EPC come�a no espa�o de memoria 0x20.
		// Comece a ler ou a escrever a partir da�.
		targetTag.setPointer(new UnsignedShort(0x30));
		// Essa � a m�scara que vamos usar para comparar o EPC.
		// Queremos corresponder a todos os bits do EPC, para que todos os bits da
		// m�scara s�o definidos.
		BitArray_HEX tagMask = new BitArray_HEX();
		targetTag.setTagMask(tagMask);
		// Queremos usar apenas tag com esse EPC.
		BitArray_HEX tagData = new BitArray_HEX();
		targetTag.setTagData(tagData);

		// Adiciona uma lista de Tags alvos com a especifica�ao da tag
		List<C1G2TargetTag> targetTagList = new ArrayList<C1G2TargetTag>();
		targetTagList.add(targetTag);
		tagSpec.setC1G2TargetTagList(targetTagList);

		// Adicionar a especifica��o da tag para o comando de acesso.
		accessCommand.setAirProtocolTagSpec(tagSpec);

		// A lista que segurar as especifica��es do Op para este comando de acesso.
		List<AccessCommandOpSpec> opSpecList = new ArrayList<AccessCommandOpSpec>();

		// Ser� que estamos lendo ou escrevendo a TAG?
		// Adicionar a especifica��o op apropriado para a lista de especifica��es op.
		// Esta funcao faz gravacao de dados na etiqueta, testada ok!.
		// opSpecList.add(buildWriteOpSpec());
		// Esta funcao faz leitura dos dados
		opSpecList.add(buildReadOpSpec());
		// opSpecList.add(buildLockC1G2());

		accessCommand.setAccessCommandOpSpecList(opSpecList);

		// Adicionar comando de acesso para spec.
		accessSpec.setAccessCommand(accessCommand);

		// Adiciona o AccessReportSpec.
		// Queremos receber uma notifica��o quando ocorre a opera��o.
		// Diga ao leitor enviar-nos com o ROSpec.
		AccessReportSpec reportSpec = new AccessReportSpec();
		reportSpec.setAccessReportTrigger(
				new AccessReportTriggerType(AccessReportTriggerType.Whenever_ROReport_Is_Generated));

		return accessSpec;
	}

	@Override
	public void messageReceived(LLRPMessage message) {
		System.out.println("contador: " + count++ + " -->Entrou na MessageReceived!");

		// textoTelaPrincipal.append("contador: " + count++ + " -->Entrou na
		// MessageReceived! \n");
		if (message.getTypeNum() == RO_ACCESS_REPORT.TYPENUM) {// se o id da mensgem for a mesma do tipo relatorio de
																// acesso

			// A messagem recebida � de um Acess Report
			RO_ACCESS_REPORT relatorio = (RO_ACCESS_REPORT) message;
			// Obter uma lista das tags de lidas.
			List<TagReportData> tags = relatorio.getTagReportDataList();

		}
	}

	@Override
	public String toString() {
		return "Middleware_LLRP SistemaMonitoramentodeAtivos";
	}
}
