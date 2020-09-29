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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RfidMiddleware implements LLRPEndpoint {

	private static Logger logger = LoggerFactory.getLogger(RfidMiddleware.class);

	private LLRPConnection reader;

	private static final int READ_ACCESSSPEC_ID = 444;
	private static final int READ_OPSPEC_ID = 1212;
	private static final int TIMEOUT_MS = 10000;
	private static final int ROSPEC_ID = 123;

	public List<String> listaLeiturasEPC = new ArrayList<String>();

	int count = 0;

	public ROSpec buildROSpec() {

		ROSpec roSpec = new ROSpec();

		roSpec.setPriority(new UnsignedByte(0));
		roSpec.setCurrentState(new ROSpecState(ROSpecState.Disabled));
		roSpec.setROSpecID(new UnsignedInteger(ROSPEC_ID));

		ROBoundarySpec roBoundarySpec = new ROBoundarySpec();

		ROSpecStartTrigger startTrig = new ROSpecStartTrigger();

		startTrig.setROSpecStartTriggerType(new ROSpecStartTriggerType(ROSpecStartTriggerType.Periodic));

		PeriodicTriggerValue periodicTrigger = new PeriodicTriggerValue();

		periodicTrigger.setPeriod(new UnsignedInteger(8000));
		periodicTrigger.setOffset(new UnsignedInteger(8000));
		startTrig.setPeriodicTriggerValue(periodicTrigger);
		roBoundarySpec.setROSpecStartTrigger(startTrig);

		ROSpecStopTrigger stopTrig = new ROSpecStopTrigger();

		stopTrig.setDurationTriggerValue(new UnsignedInteger(5000));
		stopTrig.setROSpecStopTriggerType(new ROSpecStopTriggerType(ROSpecStopTriggerType.Duration));

		roBoundarySpec.setROSpecStopTrigger(stopTrig);

		roSpec.setROBoundarySpec(roBoundarySpec);

		AISpec aispec = new AISpec();

		AISpecStopTrigger aiStopTrigger = new AISpecStopTrigger();

		aiStopTrigger.setAISpecStopTriggerType(new AISpecStopTriggerType(AISpecStopTriggerType.Null));
		aiStopTrigger.setDurationTrigger(new UnsignedInteger(0));
		aispec.setAISpecStopTrigger(aiStopTrigger);

		UnsignedShortArray antennaIDs = new UnsignedShortArray();

		antennaIDs.add(new UnsignedShort(0));
		aispec.setAntennaIDs(antennaIDs);

		InventoryParameterSpec inventoryParam = new InventoryParameterSpec();

		inventoryParam.setProtocolID(new AirProtocols(AirProtocols.EPCGlobalClass1Gen2));
		inventoryParam.setInventoryParameterSpecID(new UnsignedShort(1));
		aispec.addToInventoryParameterSpecList(inventoryParam);

		roSpec.addToSpecParameterList(aispec);

		ROReportSpec roReportSpec = new ROReportSpec();

		roReportSpec.setROReportTrigger(new ROReportTriggerType(ROReportTriggerType.Upon_N_Tags_Or_End_Of_ROSpec));
		roReportSpec.setN(new UnsignedShort(1));

		TagReportContentSelector reportContent = new TagReportContentSelector();

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

	public void addROSpec() {
		ADD_ROSPEC_RESPONSE response;

		ROSpec roSpec = buildROSpec();

		try {
			ADD_ROSPEC roSpecMsg = new ADD_ROSPEC();
			roSpecMsg.setROSpec(roSpec);
			response = (ADD_ROSPEC_RESPONSE) reader.transact(roSpecMsg, TIMEOUT_MS);

			logger.info(response.toXMLString());

			StatusCode status = response.getLLRPStatus().getStatusCode();
			if (status.equals(new StatusCode("M_Success"))) {
				logger.info("Add ROSpec adicionado com Sucesso.");
			} else {
				logger.error("Erro ao adicionar o ROSpec.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enableROSpec() {
		ENABLE_ROSPEC_RESPONSE response;

		ENABLE_ROSPEC enable = new ENABLE_ROSPEC();

		enable.setROSpecID(new UnsignedInteger(ROSPEC_ID));

		try {
			response = (ENABLE_ROSPEC_RESPONSE) reader.transact(enable, TIMEOUT_MS);

			logger.info(response.toXMLString());
			logger.info("Enable Rospec habilitado com Sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startROSpec() {
		START_ROSPEC_RESPONSE response;

		logger.info("Inciando o ROSpec.");

		START_ROSPEC start = new START_ROSPEC();
		start.setROSpecID(new UnsignedInteger(ROSPEC_ID));

		try {
			response = (START_ROSPEC_RESPONSE) reader.transact(start, TIMEOUT_MS);

			logger.info(response.toXMLString());
			logger.info("StartROSpec Iniciado com Sucesso!.");
			logger.info("Leitor Iniciado!");

		} catch (Exception e) {

			logger.error("Erro ao iniciar o StartROSpec." + "\n");
			e.printStackTrace();
		}
	}

	public void deleteROSpecs() {
		DELETE_ROSPEC_RESPONSE response;

		logger.info("Deletando todos os ROSpecs.");

		DELETE_ROSPEC del = new DELETE_ROSPEC();

		del.setROSpecID(new UnsignedInteger(0));
		try {
			response = (DELETE_ROSPEC_RESPONSE) reader.transact(del, TIMEOUT_MS);

			logger.info(response.toXMLString());

		} catch (Exception e) {

			logger.error("Erro ao deletar os ROSpec.");
			e.printStackTrace();
		}
	}

	public void errorOccured(String s) {
		logger.error(s);
	}

	public void connect(String hostname) {

		reader = new LLRPConnector(this, hostname);

		logger.info("IP:");
		logger.info(((LLRPConnector) reader).getHost());

		logger.info("PORT:");
		logger.info(Integer.toString(((LLRPConnector) reader).getPort()));

		try {
			((LLRPConnector) reader).connect();

			logger.info("Conectado com Sucesso!");

		} catch (LLRPConnectionAttemptFailedException e1) {
			logger.error("Não foi possivel conectar-se ao leitor");
			e1.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			((LLRPConnector) reader).disconnect();

			logger.info("Desconectado com Sucesso!");
		} catch (Exception e2) {

			logger.error("Não foi possível se desconectar do leitor");

			e2.printStackTrace();
		}
	}

	public void enableAccessSpec(int accessSpecID) {
		ENABLE_ACCESSSPEC_RESPONSE response;

		logger.info("Habilitando o AccessSpec.");

		ENABLE_ACCESSSPEC enable = new ENABLE_ACCESSSPEC();

		enable.setAccessSpecID(new UnsignedInteger(accessSpecID));

		try {
			response = (ENABLE_ACCESSSPEC_RESPONSE) reader.transact(enable, TIMEOUT_MS);

			logger.info(response.toXMLString());

			logger.info("o enableAccessSpec foi habilitado com sucesso.");
		} catch (Exception e) {

			logger.error("Erro ao habilitar o enableAccessSpec.");

			e.printStackTrace();
		}
	}

	public void deleteAccessSpecs() {
		logger.info("DELETAR ACESSPEC");

		DELETE_ACCESSSPEC_RESPONSE response;

		logger.info("Deletando todos os AccessSpecs.");

		DELETE_ACCESSSPEC del = new DELETE_ACCESSSPEC();

		del.setAccessSpecID(new UnsignedInteger(0));

		try {

			response = (DELETE_ACCESSSPEC_RESPONSE) reader.transact(del, TIMEOUT_MS);

			logger.info(response.toXMLString());
		} catch (Exception e) {

			logger.error("Erro ao deletar o AccessSpec.");

			e.printStackTrace();
		}
	}

	public void addAccessSpec(int accessSpecID) {
		ADD_ACCESSSPEC_RESPONSE response;

		AccessSpec accessSpec = buildAccessSpec(accessSpecID);

		logger.info("Adicionando o AccessSpec.");
		try {
			ADD_ACCESSSPEC accessSpecMsg = new ADD_ACCESSSPEC();

			accessSpecMsg.setAccessSpec(accessSpec);
			response = (ADD_ACCESSSPEC_RESPONSE) reader.transact(accessSpecMsg, TIMEOUT_MS);

			logger.info(response.toXMLString());

			StatusCode status = response.getLLRPStatus().getStatusCode();
			if (status.equals(new StatusCode("M_Success"))) {
				logger.info("Sucesso ao adicionar o AccessSpec.");

			} else {
				logger.error("Erro ao adicionar o AccessSpec.");
			}
		} catch (Exception e) {
			logger.error("Erro ao adicionar o AccessSpec.");

			e.printStackTrace();
		}
	}

	public C1G2Read buildReadOpSpec() {

		C1G2Read opSpec = new C1G2Read();

		opSpec.setOpSpecID(new UnsignedShort(READ_OPSPEC_ID));
		opSpec.setAccessPassword(new UnsignedInteger(0));

		TwoBitField opMemBank = new TwoBitField();

		opSpec.setMB(opMemBank);

		opSpec.setWordPointer(new UnsignedShort(0x20));

		opSpec.setWordCount(new UnsignedShort(32));

		return opSpec;
	}

	public AccessSpec buildAccessSpec(int accessSpecID) {
		logger.info("Construindo o AccessSpec.");

		AccessSpec accessSpec = new AccessSpec();

		accessSpec.setAccessSpecID(new UnsignedInteger(accessSpecID));

		accessSpec.setROSpecID(new UnsignedInteger(0));
		
		accessSpec.setAntennaID(new UnsignedShort(0));
		accessSpec.setProtocolID(new AirProtocols(AirProtocols.EPCGlobalClass1Gen2));
		
		accessSpec.setCurrentState(new AccessSpecState(AccessSpecState.Disabled));
		AccessSpecStopTrigger stopTrigger = new AccessSpecStopTrigger();
		
		stopTrigger.setAccessSpecStopTrigger(new AccessSpecStopTriggerType(AccessSpecStopTriggerType.Operation_Count));
		
		stopTrigger.setOperationCountValue(new UnsignedShort(0));
		accessSpec.setAccessSpecStopTrigger(stopTrigger);

		AccessCommand accessCommand = new AccessCommand();

		C1G2TagSpec tagSpec = new C1G2TagSpec();
		C1G2TargetTag targetTag = new C1G2TargetTag();
		targetTag.setMatch(new Bit(1));

		TwoBitField memBank = new TwoBitField();

		targetTag.setMB(memBank);

		targetTag.setPointer(new UnsignedShort(0x30));

		BitArray_HEX tagMask = new BitArray_HEX();
		targetTag.setTagMask(tagMask);

		BitArray_HEX tagData = new BitArray_HEX();
		targetTag.setTagData(tagData);

		List<C1G2TargetTag> targetTagList = new ArrayList<C1G2TargetTag>();
		targetTagList.add(targetTag);
		tagSpec.setC1G2TargetTagList(targetTagList);

		accessCommand.setAirProtocolTagSpec(tagSpec);

		List<AccessCommandOpSpec> opSpecList = new ArrayList<AccessCommandOpSpec>();

		opSpecList.add(buildReadOpSpec());

		accessCommand.setAccessCommandOpSpecList(opSpecList);

		accessSpec.setAccessCommand(accessCommand);

		AccessReportSpec reportSpec = new AccessReportSpec();
		reportSpec.setAccessReportTrigger(
				new AccessReportTriggerType(AccessReportTriggerType.Whenever_ROReport_Is_Generated));

		return accessSpec;
	}

	public void run(String hostname) {

		logger.info("1 - Conectando-se ao Leitor");
		connect(hostname);

		logger.info("2 - Deletando Accespecs");
		deleteAccessSpecs();

		logger.info("3 - Deletando ROSpecs");
		deleteROSpecs();

		logger.info("4 - Adicionando ROSpecs");
		addROSpec();

		logger.info("5 - Adicionando Accespecs");
		addAccessSpec(READ_ACCESSSPEC_ID);

		logger.info("6 - Ativando Accespecs");
		enableAccessSpec(READ_ACCESSSPEC_ID);

		logger.info("7 - Ativando ROSPEC");
		enableROSpec();

		logger.info("8 - Iniciando ROSPec");
		startROSpec();

	}

	public void stop() {
		logger.info("1 - Delete AccessSpecs");
		deleteAccessSpecs();

		logger.info("2 - Delete ROSPec");
		deleteROSpecs();

		logger.info("3 - Desconectando-se do Leitor");
		disconnect();
	}

	@Override
	public void messageReceived(LLRPMessage message) {
		logger.info("contador: " + count++ + " -->Entrou na MessageReceived!");
	}

	@Override
	public String toString() {
		return "Middleware_LLRP SistemaMonitoramentodeAtivos";
	}
}
