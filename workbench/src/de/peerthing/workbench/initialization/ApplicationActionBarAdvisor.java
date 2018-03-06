package de.peerthing.workbench.initialization;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.cheatsheets.OpenCheatSheetAction;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the
 * actions added to a workbench window. Each window will be populated with
 * new actions.
 *
 * @author Michael Gottschalk, Petra Beenken (user guide, help functions)
 * @review Tjark
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    // Actions - important to allocate these only in makeActions, and then use them
    // in the fill methods.  This ensures that the actions aren't recreated
    // when fillActionBars is called with FILL_PROXY.
    private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;
    private IWorkbenchAction newWindowAction;
    private IWorkbenchAction helpAction;
    private IWorkbenchAction preferencesAction;
    private IWorkbenchAction saveAction;
    private IWorkbenchAction newAction;
    private IWorkbenchAction undoAction;
    //private IWorkbenchAction guideAction;
    private IWorkbenchAction dynamicHelpAction;
    private IWorkbenchAction welcomeAction;
    private IWorkbenchAction searchAction; 
    private IWorkbenchAction redoAction;    
    private Action openCheatSheetAction;


    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(final IWorkbenchWindow window) {
        // Creates the actions and registers them.
        // Registering is needed to ensure that key bindings work.
        // The corresponding commands keybindings are defined in the plugin.xml file.
        // Registering also provides automatic disposal of the actions when
        // the window is closed.

        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);

        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);

        newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
        register(newWindowAction);
        
        helpAction = ActionFactory.HELP_CONTENTS.create(window);
        register(helpAction);
        
        preferencesAction = ActionFactory.PREFERENCES.create(window);
        register(preferencesAction);
        
        saveAction = ActionFactory.SAVE.create(window);
        register(saveAction);
        
        newAction = ActionFactory.NEW.create(window);
        register(newAction);
        
        undoAction = ActionFactory.UNDO.create(window);
        register(undoAction);
        
        redoAction = ActionFactory.REDO.create(window);
        register(redoAction);
        
        dynamicHelpAction = ActionFactory.DYNAMIC_HELP.create(window);
        register(dynamicHelpAction);

        welcomeAction = ActionFactory.INTRO.create(window);
        register(welcomeAction);    

        searchAction = ActionFactory.HELP_SEARCH.create(window);
        register(searchAction);
        
        openCheatSheetAction = new OpenCheatSheetAction("de.peerthing.workbench.cheatsheet");
        openCheatSheetAction.setText("Tutorial");
    }

    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
        MenuManager editMenu = new MenuManager("&Edit", IWorkbenchActionConstants.M_EDIT);
        MenuManager windowMenu = new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW);
        MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
        
        menuBar.add(fileMenu);
        // Add a group marker indicating where action set menus will appear.
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(editMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);

        // File
        fileMenu.add(new GroupMarker(ActionFactory.NEW.getId()));
        fileMenu.add(newAction);
        fileMenu.add(saveAction);
        fileMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);
        
        // Edit
        editMenu.add(undoAction);
        editMenu.add(redoAction);
        
        // Window
        windowMenu.add(preferencesAction);

        // Help
        helpMenu.add(welcomeAction);
        helpMenu.add(new Separator());  
        helpMenu.add(new GroupMarker(IWorkbenchActionConstants.HELP_START));
        helpMenu.add(helpAction);
        helpMenu.add(dynamicHelpAction);
        helpMenu.add(searchAction);
        helpMenu.add(openCheatSheetAction);
        helpMenu.add(new Separator());        
        helpMenu.add(aboutAction);    
    
    
    }

    protected void fillCoolBar(ICoolBarManager coolBar) {
        IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
        coolBar.add(new ToolBarContributionItem(toolbar, "main"));
    }
}
