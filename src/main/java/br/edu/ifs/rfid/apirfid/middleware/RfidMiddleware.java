package br.edu.ifs.rfid.apirfid.middleware;

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
import org.llrp.ltk.generated.messages.DELETE_ROSPEC;
import org.llrp.ltk.generated.messages.ENABLE_ACCESSSPEC;
import org.llrp.ltk.generated.messages.ENABLE_ROSPEC;
import org.llrp.ltk.generated.messages.RO_ACCESS_REPORT;
import org.llrp.ltk.generated.messages.START_ROSPEC;
import org.llrp.ltk.generated.parameters.AISpec;
import org.llrp.ltk.generated.parameters.AISpecStopTrigger;
import org.llrp.ltk.generated.parameters.AccessCommand;
import org.llrp.ltk.generated.parameters.AccessReportSpec;
import org.llrp.ltk.generated.parameters.AccessSpec;
import org.llrp.ltk.generated.parameters.AccessSpecStopTrigger;
import org.llrp.ltk.generated.parameters.C1G2Read;
import org.llrp.ltk.generated.parameters.C1G2TagSpec;
import org.llrp.ltk.generated.parameters.C1G2TargetTag;
import org.llrp.ltk.generated.parameters.EPC_96;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Active;
import br.edu.ifs.rfid.apirfid.domain.MovementHistory;
import br.edu.ifs.rfid.apirfid.domain.Tag;
import br.edu.ifs.rfid.apirfid.service.ActiveService;
import br.edu.ifs.rfid.apirfid.service.TagService;
import br.edu.ifs.rfid.apirfid.shared.FnUtil;
import lombok.Data;

@Service
@Data
public class RfidMiddleware implements LLRPEndpoint {

	private static Logger logger = LoggerFactory.getLogger(RfidMiddleware.class);

	private LLRPConnection reader;

	private static final int READ_ACCESSSPEC_ID = 444;
	private static final int READ_OPSPEC_ID = 1212;
	private static final int TIMEOUT_MS = 10000;
	private static final int ROSPEC_ID = 123;

	protected static final List<String> epcList = new ArrayList<>();

	int count = 0;

	private int realizarAtualizacaoEstoque;

	private TagService tagService;
	private ActiveService activeService;

	@Autowired
	public RfidMiddleware(TagService tagService, ActiveService activeService) {
		this.tagService = tagService;
		this.activeService = activeService;
	}

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

		List<C1G2TargetTag> targetTagList = new ArrayList<>();
		targetTagList.add(targetTag);
		tagSpec.setC1G2TargetTagList(targetTagList);

		accessCommand.setAirProtocolTagSpec(tagSpec);

		List<AccessCommandOpSpec> opSpecList = new ArrayList<>();

		opSpecList.add(buildReadOpSpec());

		accessCommand.setAccessCommandOpSpecList(opSpecList);

		accessSpec.setAccessCommand(accessCommand);

		AccessReportSpec reportSpec = new AccessReportSpec();
		reportSpec.setAccessReportTrigger(
				new AccessReportTriggerType(AccessReportTriggerType.Whenever_ROReport_Is_Generated));

		return accessSpec;
	}

	public Boolean addROSpec() {
		try {

			ADD_ROSPEC_RESPONSE response;

			ROSpec roSpec = buildROSpec();

			ADD_ROSPEC roSpecMsg = new ADD_ROSPEC();

			roSpecMsg.setROSpec(roSpec);
			response = (ADD_ROSPEC_RESPONSE) reader.transact(roSpecMsg, TIMEOUT_MS);

			StatusCode status = response.getLLRPStatus().getStatusCode();

			if (!status.equals(new StatusCode("M_Success"))) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;

		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public Boolean enableROSpec() {
		try {

			ENABLE_ROSPEC enable = new ENABLE_ROSPEC();

			enable.setROSpecID(new UnsignedInteger(ROSPEC_ID));

			reader.transact(enable, TIMEOUT_MS);

			return Boolean.TRUE;

		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public Boolean startROSpec() {

		try {

			START_ROSPEC start = new START_ROSPEC();

			start.setROSpecID(new UnsignedInteger(ROSPEC_ID));

			reader.transact(start, TIMEOUT_MS);

			logger.info("Reader Started!");

			return Boolean.TRUE;

		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public Boolean deleteROSpecs() {
		try {

			DELETE_ROSPEC del = new DELETE_ROSPEC();

			del.setROSpecID(new UnsignedInteger(0));

			reader.transact(del, TIMEOUT_MS);

			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public void errorOccured(String s) {
		logger.error(s);
	}

	public Boolean connect(String hostname) {

		try {

			reader = new LLRPConnector(this, hostname);

			((LLRPConnector) reader).connect();

			logger.info("Connected successfully!");

			return Boolean.TRUE;

		} catch (LLRPConnectionAttemptFailedException e) {
			return Boolean.FALSE;
		}
	}

	public Boolean disconnect() {
		try {

			((LLRPConnector) reader).disconnect();

			logger.info("Successfully Disconnected!");

			return Boolean.TRUE;

		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public Boolean enableAccessSpec(int accessSpecID) {

		try {

			ENABLE_ACCESSSPEC enable = new ENABLE_ACCESSSPEC();

			enable.setAccessSpecID(new UnsignedInteger(accessSpecID));

			reader.transact(enable, TIMEOUT_MS);

			return Boolean.TRUE;

		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public Boolean deleteAccessSpecs() {

		try {

			DELETE_ACCESSSPEC del = new DELETE_ACCESSSPEC();

			del.setAccessSpecID(new UnsignedInteger(0));

			reader.transact(del, TIMEOUT_MS);

			return Boolean.TRUE;

		} catch (Exception e) {

			return Boolean.FALSE;
		}
	}

	public Boolean addAccessSpec(int accessSpecID) {

		try {

			ADD_ACCESSSPEC_RESPONSE response;

			AccessSpec accessSpec = buildAccessSpec(accessSpecID);

			ADD_ACCESSSPEC accessSpecMsg = new ADD_ACCESSSPEC();

			accessSpecMsg.setAccessSpec(accessSpec);

			response = (ADD_ACCESSSPEC_RESPONSE) reader.transact(accessSpecMsg, TIMEOUT_MS);

			StatusCode status = response.getLLRPStatus().getStatusCode();

			if (status.equals(new StatusCode("M_Success"))) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}

		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public Boolean run(String hostname) {

		try {

			logger.info("1 - Connecting to the Reader");
			Boolean connect = connect(hostname);

			if (Boolean.FALSE.equals(connect))
				return Boolean.FALSE;

			logger.info("2 - Deselecting Accespecs");
			deleteAccessSpecs();

			logger.info("3 - DeleteROSpecs");
			deleteROSpecs();

			logger.info("4 - Adding ROSpecs");
			addROSpec();

			logger.info("5 - Adding Accespecs");
			addAccessSpec(READ_ACCESSSPEC_ID);

			logger.info("6 - Activating Accespecs");
			enableAccessSpec(READ_ACCESSSPEC_ID);

			logger.info("7 - Activating ROSPEC");
			enableROSpec();

			logger.info("8 - Starting ROSPec");
			startROSpec();

			return Boolean.TRUE;

		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public Boolean stop() {
		try {

			logger.info("1 - Delete AccessSpecs");
			Boolean deleteAccessSpecs = deleteAccessSpecs();
			if (Boolean.FALSE.equals(deleteAccessSpecs))
				return Boolean.FALSE;

			logger.info("2 - Delete ROSPec");
			deleteROSpecs();

			logger.info("3 - Disconnecting from the Reader");
			disconnect();

			return Boolean.TRUE;

		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public Boolean inserirNoHistorico(String epc, Boolean calculaTempo) {

		try {

			/**
			 * 0 - Out 1 - In
			 */

			// Search for registered tag with EPC code captured by reader
			Tag tag = tagService.getTagByEpc(epc);

			if (tag == null) {

				logger.info("EPC: " + epc + " NOT REGISTERED");

				return Boolean.FALSE;

			}

			logger.info("EPC: " + epc + " REGISTERED - SEEKING ASSET ASSOCIATED WITH TAG");

			// Search for a registered asset
			Active active = activeService.getActiveByTagId(tag.getId());

			if (active == null) {

				logger.info("ACTIVE DOES NOT EXIST");

				return Boolean.FALSE;

			}

			// Retrieving last asset movement
			MovementHistory movementHistory = activeService.getLastMovmentHistoryByActiveId(active.getId());

			if (movementHistory == null) {

				// If there is no movement history, the asset is exiting
				activeService.updateMovimentacao(0, active.getId(), active.getNumeroPatrimonio());

				return Boolean.TRUE;
			}

			Boolean isToMove = FnUtil.isToMove(calculaTempo, FnUtil.diferencaEmMinutos(movementHistory));

			if (Boolean.TRUE.equals(isToMove)) {

				logger.info(active.getModelo() + " - It's been five minutes since the last move... Moving");

				if (active.getLastMovimentacao() == 1) {

					activeService.updateMovimentacao(0, active.getId(), active.getNumeroPatrimonio());

					return Boolean.TRUE;
				} else {

					activeService.updateMovimentacao(1, active.getId(), active.getNumeroPatrimonio());

					return Boolean.TRUE;
				}
			}

			logger.info("LAST MOVEMENT: " + active.getLastMovimentacao());

			return Boolean.TRUE;

		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	@Override
	public void messageReceived(LLRPMessage message) {

		Boolean calcularTempo = false;

		// logger.info("Count: " + count++ + " --> Joined MessageReceived!");

		if (message.getTypeNum() == RO_ACCESS_REPORT.TYPENUM) {

			RO_ACCESS_REPORT relatorio = (RO_ACCESS_REPORT) message;

			List<TagReportData> tags = relatorio.getTagReportDataList();

			for (TagReportData tag : tags) {

				String epc = ((EPC_96) tag.getEPCParameter()).getEPC().toString();

				logger.info("captured EPC code: " + epc);

				if (!RfidMiddleware.epcList.contains(epc)) {

					RfidMiddleware.epcList.add(epc);

					logger.info("Code: " + epc + " added on epcList");

					inserirNoHistorico(epc, calcularTempo);

				} else {

					// If an epc already exists in the list, check if there are 5 minutes since the
					// last move
					calcularTempo = true;

					logger.info("EPC code is in the list: " + epc);

					inserirNoHistorico(epc, calcularTempo);

				}
			}
		}
	}

	@Override
	public String toString() {
		return "Middleware_LLRP SistemaMonitoramentodeAtivos";
	}
}
