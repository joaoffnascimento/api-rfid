package br.edu.ifs.rfid.apirfid.shared;

public class Constants {

	private static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error, please contact our support";

	private static final String READER_NOT_FOUND_ERROR = "Reader not found!";
	

	private static final String DELETE = "DELETE";

	private static final String UPDATE = "PUT";
	
	/**
	 * Reader
	 */
	
	private static final String IP_REQUIRED = "IP is required";

	private static final String PORT_REQUIRED = "PORT is required";

	private Constants() {

	}

	public static String getInternalServerErrorMsg() {
		return INTERNAL_SERVER_ERROR_MSG;
	}

	public static String getReaderNotFoundError() {
		return READER_NOT_FOUND_ERROR;
	}

	public static String getDelete() {
		return DELETE;
	}

	public static String getUpdate() {
		return UPDATE;
	}

	public static String getIpRequired() {
		return IP_REQUIRED;
	}

	public static String getPortRequired() {
		return PORT_REQUIRED;
	}
	
	
}
