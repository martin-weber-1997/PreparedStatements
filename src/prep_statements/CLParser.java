package prep_statements;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * parses command line options using Apache Commons CLI 1.31
 * 
 * @author Daniel May, Martin Weber
 * @version 20160224.2
 *
 */
public class CLParser {

	private CommandLine cl;
	private Options opt;
	private HelpFormatter formatter;
	private String password;
	private Properties prop;

	/**
	 * constructor
	 */
	public CLParser(String[] args) {
		setUpOptions();
		formatter = new HelpFormatter();
		parseOptions(args);
		help();
		loadProperties();
	}

	/**
	 * setting up the command line options
	 */
	private void setUpOptions() {
		opt = new Options();
		opt.addOption(Option.builder("h").desc("displays this message").longOpt("help").build());
		opt.addOption(Option.builder("a").argName("data-rows").desc("ammount of rows to use for the CRUD operations")
				.hasArg().longOpt("amount").numberOfArgs(1).build());
		opt.addOption(Option.builder("f").argName("path-to-file").desc("the filepath of the property file").hasArg()
				.longOpt("file").numberOfArgs(1).build());
		opt.addOption(Option.builder("s").desc("show data from select query").longOpt("show").build());
		opt.addOption(Option.builder("d").argName("database-name").desc("database name to connect to").hasArg()
				.longOpt("database").numberOfArgs(1).build());
		opt.addOption(Option.builder("H").argName("hostname").desc("database server host").hasArg().longOpt("host")
				.numberOfArgs(1).build());
		opt.addOption(Option.builder("p").argName("port").desc("database server port").hasArg().longOpt("port")
				.numberOfArgs(1).build());
		opt.addOption(Option.builder("u").argName("username").desc("database user name").hasArg().longOpt("user")
				.numberOfArgs(1).build());

		OptionGroup pwGroup = new OptionGroup();
		pwGroup.addOption(Option.builder("w").argName("password").desc("pass password as command line argument")
				.hasArg().longOpt("password").numberOfArgs(1).build());
		pwGroup.addOption(Option.builder("W").desc("force password prompt").longOpt("password-prompt").build());
		opt.addOptionGroup(pwGroup);
	}

	/**
	 * Loads the properties from a file. If the File is not found or a
	 * IOException occurs, a error message will be printed out.
	 */
	private void loadProperties() {
		if (cl.hasOption("f")) {
			try (FileReader fr = new FileReader(cl.getOptionValue("f"))) {
				prop = new Properties();
				prop.load(fr);
			} catch (FileNotFoundException e) {
				System.err.println("Properties File doesn't exist");
				System.err.println(e.getMessage());
			} catch (IOException e1) {
				System.err.println("Can't read Property File");
				System.err.println(e1.getMessage());
			}
		}
	}

	/**
	 * Parse command line arguments. If a ParseException occurs, the error
	 * message and the usage will be printed. The application will be terminated
	 * in that case.
	 * 
	 * @param args
	 *            command line anrguments
	 */
	private void parseOptions(String[] args) {
		DefaultParser parser = new DefaultParser();
		try {
			cl = parser.parse(opt, args);
		} catch (ParseException exp) {
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
			printUsage();
			System.exit(-1);
		}
		passwordPrompt();
	}

	/**
	 * Simple wrapper method in order to print the help message including the
	 * usage.
	 */
	private void printUsage() {
		System.out.println(
				"Every option which is not defined with the CLI arguments will be filled with default values from the Property File inside the jar file");
		formatter.printHelp("preps", opt, true);
	}

	/**
	 * Detects if a password prompt is forced and executes the prompt.
	 */
	private void passwordPrompt() {
		if (cl.hasOption('W')) {
			Console console = System.console();
			/*
			 * stackoverflow helped me
			 * http://stackoverflow.com/questions/8138411/masking-password-input
			 * -from-the-console-java
			 */
			if (console == null) {
				System.out.println("Couldn't get console instance!");
				System.exit(-1);
			}
			/*
			 * %n is a line separator; %s is for unicode characters
			 */
			password = new String(console.readPassword("Please enter your password: "));
		}
	}

	/**
	 * Gets the database specified by the command line.
	 * 
	 * @return the specified database
	 */
	public String getDatabase() {
		if (cl.hasOption("d"))
			return cl.getOptionValue('d');
		else {
			if (prop == null || !prop.containsKey("database")) {
				System.err.println("Missing option database");
				System.exit(-1);
				return "";
			} else {
				return prop.getProperty("database");
			}
		}

	}

	/**
	 * Gets the hostname specified by the command line.
	 * 
	 * @return the specified hostname
	 */
	public String getHost() {
		if (cl.hasOption("H"))
			return cl.getOptionValue('H');
		else {
			if (prop == null || !prop.containsKey("host")) {
				System.err.println("Missing option host");
				System.exit(-1);
				return "";
			} else {
				return prop.getProperty("host");
			}
		}
	}

	/**
	 * Gets the username specified by the command line.
	 * 
	 * @return the specified username
	 */
	public String getUser() {
		if (cl.hasOption("u"))
			return cl.getOptionValue('u');
		else {
			if (prop == null || !prop.containsKey("user")) {
				System.err.println("Missing option user");
				System.exit(-1);
				return "";
			} else {
				return prop.getProperty("user");
			}
		}
	}

	/**
	 * Detects if the password was specified as argument or entered via password
	 * prompt and returns it.
	 * 
	 * @return the specified password
	 */
	public String getPassword() {
		if (cl.hasOption('w'))
			return cl.getOptionValue('w');
		else if (cl.hasOption("W"))
			return password;
		else {
			if (prop == null || !prop.containsKey("password")) {
				System.err.println("Missing option password");
				System.exit(-1);
				return "";
			} else {
				return prop.getProperty("password");
			}
		}
	}

	/**
	 * Gets the port if specified by the command line, otherwise the standard
	 * port for PSQL 5432 will be used. If the specified port number is not an
	 * integer the usage will be printed and the application will be terminated.
	 * 
	 * @return the specified port
	 */
	public int getPort() {
		if (cl.hasOption("p")) {
			try {
				return Integer.parseInt(cl.getOptionValue('p'));
			} catch (NumberFormatException nfe) {
				printUsage();
				System.exit(-1);
				return 0;
			}
		} else {
			if (prop == null || !prop.containsKey("port")) {
				return 5432;
			} else {
				try {
					return Integer.parseInt(prop.getProperty("port"));
				} catch (NumberFormatException nfe) {
					System.err.println("Provided port is not an Integer.");
					System.exit(-1);
					return 0;
				}
			}
		}
	}

	/**
	 * Gets if the data from the select query should be displayed or not. If the
	 * value in the property file is not true or True this method will return
	 * false. If nothing is specified the result won't be printed out.
	 * 
	 * @return if the data should be displayed
	 */
	public boolean showData() {
		if (cl.hasOption("s"))
			return true;
		else {
			if (prop == null || !prop.containsKey("show")) {
				return false;
			} else {
				return Boolean.parseBoolean(prop.getProperty("show"));
			}
		}
	}

	/**
	 * Get the amount of specified data rows. If the amount is below 10.000 or
	 * not specified, it will be set to 10.000.
	 * 
	 * @return the amount of data rows
	 */
	public int getAmount() {
		if (cl.hasOption("a")) {
			try {
				int tmp = Integer.parseInt(cl.getOptionValue('a'));
				if (tmp < 10000)
					return 10000;
				else
					return tmp;
			} catch (NumberFormatException nfe) {
				printUsage();
				System.exit(-1);
				return 0;
			}
		} else {
			if (prop != null && prop.containsKey("amount")) {
				try {
					int tmp = Integer.parseInt(prop.getProperty("amount"));
					if (tmp < 10000)
						return 10000;
					else
						return tmp;
				} catch (NumberFormatException nfe) {
					printUsage();
					System.exit(-1);
					return 0;
				}
			} else {
				return 10000;
			}
		}
	}

	/**
	 * checks if the application help is called. If this is the case, the
	 * application will terminate after displaying the message.
	 */
	private void help() {
		if (cl.hasOption('h')) {
			printUsage();
			System.exit(0);
		}
	}

	/**
	 * Sets the password field to null.
	 */
	public void clearPassword() {
		password = null;
	}
}