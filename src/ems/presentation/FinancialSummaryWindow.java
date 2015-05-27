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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


import ems.business.AccessTitle;
import ems.business.CalculateHours;
import ems.business.FinancialCalculator;
import ems.models.Title;

import java.util.ArrayList;
import org.eclipse.wb.swt.SWTResourceManager;

public class FinancialSummaryWindow {
	private Shell shlEmsEmployee;
	private Display display;
	
	
	//Containers for GUI components
	private Composite botPanel;
	private Composite midPanel;
	
	//GUI components
	private Button btnClose;
	private Table tblSummary;
	private TableColumn tblclmnTile;
	private TableColumn tblclmnHoursWorked;
	private TableColumn tblclmnExpenditure;
	private FormData fd_botPanel;
	

	
	public FinancialSummaryWindow() {
	
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
		shlEmsEmployee.setSize(400, 315);
		shlEmsEmployee.setText("EMS - Finance Summary with in current pay period");
		shlEmsEmployee.setLayout(new FormLayout());
		shlEmsEmployee.setImage(SWTResourceManager.getImage(DetailsWindow.class, "/resources/EMSLogo.jpg"));
	}
	
	private void createPanels() {	
		createBotPanel();
		createMidPanel();
	}
	
	private void createMidPanel() {
		midPanel = new Composite(shlEmsEmployee, SWT.NONE);
		fd_botPanel.top = new FormAttachment(midPanel, 6);
		
		FormData fd_midPanel = new FormData(); 
		fd_midPanel.left = new FormAttachment(0);
		fd_midPanel.right = new FormAttachment(100);
		fd_midPanel.top = new FormAttachment(0);
		fd_midPanel.bottom = new FormAttachment(100, -51);
		midPanel.setLayoutData(fd_midPanel);		
	}
	
	private void createBotPanel() {
		botPanel = new Composite(shlEmsEmployee, SWT.NONE);
		botPanel.setLayout(null);

		fd_botPanel = new FormData(); 
		fd_botPanel.bottom = new FormAttachment(100);
		fd_botPanel.right = new FormAttachment(100, 0);
		fd_botPanel.left = new FormAttachment(0,0);
		botPanel.setLayoutData(fd_botPanel);
		btnClose = new Button(botPanel, SWT.NONE);
		btnClose.setBounds(147, 10, 88, 25);
		btnClose.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlEmsEmployee.close();
			}
		});
		btnClose.setText("Close");
	}
	
	
	private void createComponents() {
		createButtons();
		createTable();
	}
	
	private void createButtons() {
	}
	
	private void createTable() {

		
		tblSummary = new Table(midPanel, SWT.BORDER | SWT.FULL_SELECTION);
		tblSummary.setBounds(20, 10, 352, 217);
		tblSummary.setHeaderVisible(true);
		tblSummary.setLinesVisible(true);
		
		tblclmnTile = new TableColumn(tblSummary, SWT.NONE);
		tblclmnTile.setWidth(117);
		tblclmnTile.setText("Title");
		
		
		tblclmnHoursWorked = new TableColumn(tblSummary, SWT.NONE);
		tblclmnHoursWorked.setWidth(116);
		tblclmnHoursWorked.setText("Total Hours");
		
		tblclmnExpenditure = new TableColumn(tblSummary, SWT.NONE);
		tblclmnExpenditure.setWidth(115);
		tblclmnExpenditure.setText("Total Expenditure");
		
		AccessTitle accessTitle = new AccessTitle();
		ArrayList<Title> titles =  accessTitle.getAllTitle();
		
		for(int i = 0; i < titles .size(); i++) {
        	Title currcTitle = titles.get(i);
            TableItem item = new TableItem(tblSummary, SWT.NONE);
            double hours =CalculateHours.getTotalHoursByTitle(currcTitle);
            item.setText(0, currcTitle.getTitle());
            item.setText(1,Double.toString(hours));
            item.setText(2,"$"+Double.toString(FinancialCalculator.getTotalExpenditureByTitle(currcTitle)));
        }
		
				
		
	}

	
}