package br.edu.ifs.rfid.apirfid.shared;

public class Constants {

	private static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error, please contact our support";

	private static final String READER_NOT_FOUND_ERROR = "Reader not found!";

	private static final String DELETE = "DELETE";

	private static final String UPDATE = "PUT";

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
}
