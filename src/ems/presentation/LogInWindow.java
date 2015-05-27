package ems.presentation;
import java.util.ArrayList;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;

import ems.application.Main;
import ems.business.AccessUser;
import ems.models.Employee;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Control;


public class LogInWindow {

	protected Shell shell;
	private Display display;
	
	//Containers for GUI components
	private Composite viewPanel;
	private Composite userSelect;
	private Composite headerBox;
	
	//GUI Components
	private Combo comboUsername;
	private Text textPassword;
	private Button btnQuit;
	private Button btnLogIn;	
	private Label lblEmployeeManagement;
	private Label lblPassword;
	private Label lblToContinue;
	private Label lblIcon;
	private Label lblUsername;

	//Data Access
	private AccessUser accessUser;
	private ArrayList<Employee> users;
	

	public LogInWindow(AccessUser accessUser) {
		this.accessUser = accessUser;
	}
	
	public LogInWindow() {
		accessUser = new AccessUser();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void open() {

		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		createAppWindow();
		createPanels();
		createComponents();
	}
	
	private void createAppWindow() {
		shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.MIN);
		shell.setImage(SWTResourceManager.getImage(LogInWindow.class, "/resources/EMSLogo.jpg"));
		shell.setSize(356, 289);
		shell.setText("EMS - Login");
       
        centerWindow();
		shell.setLayout(null);
	}
	
	private void createPanels() {
		viewPanel = new Composite(shell, SWT.NONE);
		viewPanel.setBounds(-4, 3, 354, 196);
		viewPanel.setLayout(null);
		
		headerBox = new Composite(viewPanel, SWT.NONE);
		headerBox.setBounds(10, 0, 334, 80);
		
		userSelect = new Composite(viewPanel, SWT.NONE);
		userSelect.setBounds(10, 86, 334, 110);
		userSelect.setLayout(null);
	}
	
	private void createComponents() {
		createLabels();
		createCombo();
		
		textPassword = new Text(userSelect, SWT.BORDER | SWT.READ_ONLY);
		textPassword.setBounds(149, 82, 185, 26);

		createButtons();
	}
	
	private void createLabels() {
		lblIcon = new Label(headerBox, SWT.NONE);
		lblIcon.setSize(80, 80);
		lblIcon.setImage(SWTResourceManager.getImage(LogInWindow.class, "/resources/EMSLogo.jpg"));
		
		lblEmployeeManagement = new Label(headerBox, SWT.CENTER);
		lblEmployeeManagement.setBounds(79, 10, 255, 50);
		
        lblEmployeeManagement.setFont(new Font(display, "Times", 14, SWT.BOLD));
        lblEmployeeManagement.setText("Employee Management\r\nSystem");
		
		lblUsername = new Label(userSelect, SWT.NONE);
		lblUsername.setBounds(0, 56, 69, 20);
		lblUsername.setText("Username:");
		
		lblToContinue = new Label(userSelect, SWT.CENTER);
		lblToContinue.setLocation(0, 10);
		lblToContinue.setSize(330, 20);
		lblToContinue.setText("To Continue, Please Login.");
		
		lblPassword = new Label(userSelect, SWT.NONE);
		lblPassword.setBounds(149, 56, 70, 20);
		lblPassword.setText("Password:");
	}
	
	private void createCombo() {
		comboUsername = new Combo(userSelect, SWT.READ_ONLY);
		comboUsername.setBounds(0, 82, 113, 28);
		comboUsername.select(0);
		
		//Populate ComboBox with usernames in database
		users = accessUser.getUserList();
		for(int i = 0; i < users.size(); i++){
			comboUsername.add(users.get(i).getUsername() + " (" + users.get(i).getEmployeeType() + ")");
		}
		comboUsername.select(0);
	}
	
	private void createButtons() {
		btnLogIn = new Button(shell, SWT.CENTER);
		btnLogIn.setBounds(260, 213, 80, 36);
		btnLogIn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String currUserType;
				if(comboUsername.getSelectionIndex() >= 0 && comboUsername.getSelectionIndex() < users.size()){
					
					accessUser.selectUser(users.get(comboUsername.getSelectionIndex()));
					currUserType = accessUser.getUserType();
					
					shell.close();
					
					if(currUserType.equals("F")){ //Direct to finance window
						FinanceWindow financeWindow = new FinanceWindow(accessUser);
						financeWindow.open();
					}
					else if(currUserType.equals("E")){ //Direct to employee window
						EmployeeWindow employeeWindow = new EmployeeWindow(accessUser);
						employeeWindow.open();
					}
					else if(currUserType.equals("M")){ //Direct to manager window
						ManagerWindow managerWindow = new ManagerWindow(accessUser);
						managerWindow.open();
					}
					
				}
				else{
					System.out.println("Error: invalid user selection, cannot log in");
				}
				
			}
		});
		btnLogIn.setText("Log In");
		
		btnQuit = new Button(shell, SWT.CENTER);
		btnQuit.setBounds(6, 213, 80, 36);
		btnQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Main.shutDown();
				shell.close();
			}
		});
		btnQuit.setText("Quit");
		shell.setTabList(new Control[]{viewPanel, btnQuit, btnLogIn});
	}

	public void centerWindow(){
        Monitor monitor = display.getPrimaryMonitor ();
        Rectangle screenBounds = monitor.getBounds ();
        Rectangle windowBounds = shell.getBounds ();
        int x = screenBounds.x + (screenBounds.width - windowBounds.width) / 2;
        int y = screenBounds.y + (screenBounds.height - windowBounds.height) / 2;
        shell.setLocation (x, y);
	}
}
