package de.hu_berlin.ensureII.sre.parser.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVLogger {
	private final static int DEFAULT_BUFFER_SIZE = 100;
	private final static Character DEFAULT_SEPARATOR = new Character(',');
	private ArrayList<String> tuples;
	private final String[] headers;
	private final PrintWriter fOut;
	private final Character separator;
	private final int bufferSize;

	public CSVLogger(String path, Character separator, int bufferSize, String... headers) throws IOException {
		this.headers = headers;
		this.separator = separator;
		this.bufferSize = bufferSize;
		FileWriter fileWriter = new FileWriter(path);
		this.fOut = new PrintWriter(fileWriter);
		tuples = new ArrayList<String>((int) 1.1 * bufferSize + 1);
		writeHeaders();
	}

	public CSVLogger(String path, Character separator, String... headers) throws IOException {
		this(path, separator, DEFAULT_BUFFER_SIZE, headers);
	}

	public CSVLogger(String path, int bufferSize, String... headers) throws IOException {
		this(path, DEFAULT_SEPARATOR, bufferSize, headers);
	}

	public CSVLogger(String path, String... headers) throws IOException {
		this(path, DEFAULT_SEPARATOR, DEFAULT_BUFFER_SIZE, headers);
	}

	private void writeHeaders() {
		for (int i = 0; i < headers.length - 1; i++) {
			fOut.print(headers[i] + this.separator);
		}
		fOut.println(headers[headers.length - 1]);
		fOut.flush();
	}

	public synchronized void log(Object... tuple) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int entryIndex = 0; entryIndex < tuple.length - 1; entryIndex++) {
			stringBuilder.append(tuple[entryIndex].toString() + separator);
		}
		stringBuilder.append(tuple[tuple.length - 1].toString());
		tuples.add(stringBuilder.toString());
		if (tuples.size() > bufferSize) {
			flush();
		}
	}

	public synchronized void flush() {
		for (String tuple : tuples) {
			fOut.println(tuple);
		}
		tuples.clear();
		fOut.flush();
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	public synchronized void close() {
		flush();
		fOut.close();
	}
}