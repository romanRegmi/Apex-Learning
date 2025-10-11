/*
🔹 𝗖𝗼𝗺𝗺𝗮𝗻𝗱 𝗣𝗮𝘁𝘁𝗲𝗿𝗻 𝗶𝗻 𝗔𝗽𝗲𝘅 – 𝗘𝗻𝗰𝗮𝗽𝘀𝘂𝗹𝗮𝘁𝗲 𝗥𝗲𝗾𝘂𝗲𝘀𝘁𝘀 𝗳𝗼𝗿 𝗠𝗮𝘅 𝗙𝗹𝗲𝘅𝗶𝗯𝗶𝗹𝗶𝘁𝘆! 🔹

Ever wanted to decouple request execution from request initiation? Do you need undo functionality, queueing, or dynamic command execution in Salesforce?

✅ 𝗦𝗼𝗹𝘂𝘁𝗶𝗼𝗻? 𝗨𝘀𝗲 𝘁𝗵𝗲 𝗖𝗼𝗺𝗺𝗮𝗻𝗱 𝗣𝗮𝘁𝘁𝗲𝗿𝗻!

🔹 𝗪𝗵𝗮𝘁 𝗶𝘀 𝘁𝗵𝗲 𝗖𝗼𝗺𝗺𝗮𝗻𝗱 𝗣𝗮𝘁𝘁𝗲𝗿𝗻?
The Command Pattern encapsulates a request as an object, allowing you to parametrize, queue, and log requests while enabling undo operations.

🔥 𝗪𝗵𝘆 𝗨𝘀𝗲 𝗖𝗼𝗺𝗺𝗮𝗻𝗱 𝗶𝗻 𝗔𝗽𝗲𝘅?
🔹 Decouples sender (Invoker) and receiver (Executor).
🔹 Allows batch execution of commands.
🔹 Enables undo functionality (reversible operations).
🔹 Simplifies integration with event-driven architectures.

🔥 𝗞𝗲𝘆 𝗧𝗮𝗸𝗲𝗮𝘄𝗮𝘆𝘀:
✅ Encapsulates operations as objects.
✅ Enables batch processing of commands.
✅ Supports undo functionality (if implemented).
✅ Makes execution flexible & extensible.

💡 𝗨𝘀𝗲 𝗰𝗮𝘀𝗲𝘀:
🔹 Bulk actions like mass record updates.
🔹 Workflow automation where actions vary dynamically.
🔹 Undo operations (e.g., reverting changes).
🔹 Integration queueing for external APIs.
*/

/* Without Command Pattern */
public class AccountService {
    public static void createAccount(String name) {
        Account acc = new Account(Name = name);
        insert acc;
    }
}

public class ContactService {
    public static void createContact(String firstName, String lastName) {
        Contact con = new Contact(FirstName = firstName, LastName = lastName);
        insert con;
    }
}

// Directly calling services - No flexibility
AccountService.createAccount('Acme Corp');
ContactService.createContact('John', 'Doe');


/* With Command Pattern */
// Step 1 : Define the Command Interface
public interface ICommand {
    void execute();
}

// Step 2 : Implement Specific Commands
public class CreateAccountCommand implements ICommand {
    private String accName;
    public CreateAccountCommand(String name) {
        this.accName = name;
    }

    public void execute() {
        Account acc = new Account(Name = accName);
        insert acc;
    }
}

public class CreateContactCommand implements ICommand {
    private String firstName, lastName;
    public CreateContactCommand(String name) {
        this.firstName = fName;
        this.lastName = lName;
    }

    public void execute() {
        Contact con = new Contact(FirstName = firstName, LastName = lastName);
        insert con;
    }
}

// Step 3 : Invoker Class - Executes Commands
public class CommandInvoker {
    private List<ICommand> commands = new List<ICommand>();
    public void addCommand(ICommand command) {
        commands.add(command);
    }

    public void executeCommands() {
        for(ICommand command : commands) {
            command.execute();
        }

        commands.clear(); // Reset after execution
    }
}

// Step 4 : Use the Invoker to Execute Commands
CommandInvoker invoker = new CommandInvoker();
invoker.addCommand(new CreateAccountCommand('Acme Corp'));
invoker.addCommand(new CreateContactCommand('John', 'Doe'));
invoker.executeCommands(); // Executes all commands at once!
