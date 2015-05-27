package ems.presentation;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ems.business.AccessProject;
import ems.business.CalculateHours;
import ems.business.FinancialCalculator;
import ems.models.Project;

import java.util.ArrayList;
import org.eclipse.wb.swt.SWTResourceManager;

public class WorkReportWindow {
	private Shell shlEmsEmployee;
	private Display display;
	
	private final int BOT_PANEL_HEIGHT = 40;
	
	//Containers for GUI components
	private Composite botPanel;
	private Composite midPanel;
	
	//GUI components
	private Button btnClose;
	private Table tblProjectHours;
	private TableColumn tblclmnProjectName;
	private TableColumn tblclmnHoursWorked;
	private TableColumn tblclmnPercentage;
	
	//Data Access
	private AccessProject accessProject;
	private ArrayList<Project> projects;
	
	public WorkReportWindow() {
		accessProject = new AccessProject();
		projects = new ArrayList<Project>();
	}
	
	public void open() {
		display = Display.getDefault();
		createContents();
		shlEmsEmployee.open();
		shlEmsEmployee.layout();
		while (!shlEmsEmployee.isDisposed()) {
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
		shlEmsEmployee = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.MIN);
		shlEmsEmployee.setSize(400, 300);
		shlEmsEmployee.setText("EMS - Work reports");
		shlEmsEmployee.setLayout(new FormLayout());
		shlEmsEmployee.setImage(SWTResourceManager.getImage(DetailsWindow.class, "/resources/EMSLogo.jpg"));
	}
	
	private void createPanels() {	
		createBotPanel();
		createMidPanel();
	}
	
	private void createMidPanel() {
		midPanel = new Composite(shlEmsEmployee, SWT.NONE);
		
		FormData fd_midPanel = new FormData(); 
		fd_midPanel.top = new FormAttachment(0);
		fd_midPanel.bottom = new FormAttachment(100, -BOT_PANEL_HEIGHT);
		fd_midPanel.right = new FormAttachment(100,0);
		fd_midPanel.left = new FormAttachment(0,0);
		midPanel.setLayoutData(fd_midPanel);		
	}
	
	private void createBotPanel() {
		botPanel = new Composite(shlEmsEmployee, SWT.NONE);
		RowLayout rl_botPanel = new RowLayout(SWT.HORIZONTAL);
		rl_botPanel.wrap = false;
		botPanel.setLayout(rl_botPanel);

		FormData fd_botPanel = new FormData(); 
		fd_botPanel.top = new FormAttachment(100, -BOT_PANEL_HEIGHT);
		fd_botPanel.right = new FormAttachment(100, 0);
		fd_botPanel.left = new FormAttachment(0,0);
		fd_botPanel.bottom = new FormAttachment(100, 0);
		botPanel.setLayoutData(fd_botPanel);
	}
	
	
	private void createComponents() {
		createButtons();
		createTable();
	}
	
	private void createButtons() {
		btnClose = new Button(botPanel, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlEmsEmployee.close();
			}
		});
		btnClose.setLayoutData(new RowData(75, BOT_PANEL_HEIGHT -8));
		btnClose.setText("Close");
	}
	
	private void createTable() {

		
		tblProjectHours = new Table(midPanel, SWT.BORDER | SWT.FULL_SELECTION);
		tblProjectHours.setBounds(20, 10, 352, 217);
		tblProjectHours.setHeaderVisible(true);
		tblProjectHours.setLinesVisible(true);
		
		tblclmnProjectName = new TableColumn(tblProjectHours, SWT.NONE);
		tblclmnProjectName.setWidth(117);
		tblclmnProjectName.setText("Project Name");
		
		
		tblclmnHoursWorked = new TableColumn(tblProjectHours, SWT.NONE);
		tblclmnHoursWorked.setWidth(116);
		tblclmnHoursWorked.setText("Total Hours");
		
		tblclmnPercentage = new TableColumn(tblProjectHours, SWT.NONE);
		tblclmnPercentage.setWidth(115);
		tblclmnPercentage.setText("Relative Time (%)");
				
		projects = accessProject.getAllProjects();
		
		
        for(int i = 0; i < projects.size(); i++) {
        	Project currProject = projects.get(i);
            TableItem item = new TableItem(tblProjectHours, SWT.NONE);
            
            float totalHours = CalculateHours.getHoursByProject(currProject);
            String percentString = Double.toString(FinancialCalculator.calculatePercentage(totalHours, CalculateHours.getAllHours()));
            item.setText(0, currProject.getName());
            item.setText(1, Float.toString(totalHours));
            item.setText(2,percentString);
        }
	}

	
}