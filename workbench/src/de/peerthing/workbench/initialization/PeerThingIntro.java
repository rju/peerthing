package de.peerthing.workbench.initialization;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.forms.HyperlinkSettings;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.ui.part.IntroPart;


/**
 * This class creates the welcome desktop in PeerThing.
 *
 * @author Petra Beenken
 */

public class PeerThingIntro extends IntroPart {

    /**
     * listener for hyperlink events
     */
    private final class IntroHyperlinkListener extends HyperlinkAdapter{
        
        public void linkActivated(HyperlinkEvent e){
            //get the window and workbench
            IWorkbenchWindow window = getIntroSite().getWorkbenchWindow();
            IWorkbench workbench = window.getWorkbench();
            String href = (String) e.getHref();
            
            if(href.equals("start")){
                //call intro manager, close intro
                IIntroManager manager = workbench.getIntroManager();
                manager.closeIntro(PeerThingIntro.this);
            }else{
                // call extern browser to receive homepage of PeerThing
                IWorkbenchBrowserSupport browserSupport = workbench.getBrowserSupport();
                try{
                    IWebBrowser browser = browserSupport.getExternalBrowser();
                    browser.openURL(new URL(href));
                }catch(PartInitException ex){
                    //ignore :-)
                }catch(MalformedURLException ex){
                    //ignore too
                }
            }
        }
    }
    
    
    // form widget
    Form introForm;

    public void setFocus() {
        introForm.setFocus();
    }

    /**
     * @deprecated only for tests
     * @param container
     */
    public void createPartControl2 (Composite container) {         
        Composite outerContainer = new Composite(container, SWT.NONE);         
        GridLayout gridLayout = new GridLayout();         
        outerContainer.setLayout(gridLayout);         
        outerContainer.setBackground
            (outerContainer.getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));         
        Label label = new Label(outerContainer, SWT.CENTER);         
        label.setText("WELCOME TO PEERTHING");         
        GridData gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);         
        gd.horizontalAlignment = GridData.CENTER;         
        gd.verticalAlignment = GridData.CENTER;
        label.setLayoutData(gd);         
        label.setBackground(outerContainer.getDisplay().
                getSystemColor(SWT.COLOR_MAGENTA));     
            
        }

    
    public void createPartControl(Composite parent) {
        
        // toolkit
        Display display = parent.getDisplay();
        FormToolkit tk = new FormToolkit(display);
        //background
        tk.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));        
        
        // create form, set layout
        introForm = tk.createForm(parent);
        
        //background image
        //Image image = new Image(display, "product_lg.gif");
        //introForm.setBackgroundImage(image);
        
        TableWrapLayout layout = new TableWrapLayout();
        layout.leftMargin = 100;
        layout.rightMargin = 180;
        layout.topMargin = 80;
        
        introForm.getBody().setLayout(layout);
               
        // * * section 1 ** * /
        // create form text 
        FormText tx = tk.createFormText(introForm.getBody(), true);
        tx.setParagraphsSeparated(true);
        // hyperlink look
        HyperlinkSettings settings = new HyperlinkSettings(parent.getDisplay());
        settings.setHyperlinkUnderlineMode(HyperlinkSettings.UNDERLINE_HOVER);
        settings.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
        //settings.setForeground(display.getSystemColor(SWT.COLOR_BLUE));        
        settings.setActiveForeground(display.getSystemColor(SWT.COLOR_DARK_RED));
        tx.setHyperlinkSettings(settings);
        // fonts
        Font titleFont = JFaceResources.getFont(JFaceResources.HEADER_FONT);
        tx.setFont("title", titleFont);
        Font subtitleFont = JFaceResources.getFont(JFaceResources.BANNER_FONT);
        tx.setFont("subtitle", subtitleFont);
        // color title below
        tx.setColor("title", display.getSystemColor(SWT.COLOR_DARK_RED));
        // reactions for hyperlink events
        tx.addHyperlinkListener(new IntroHyperlinkListener());
        // Text mit Mark-up
        String text = "<form>"
                + "<p><span color=\"title\" font=\"title\">Welcome to PeerThing</span></p>"
                //+ "<p><img src=\"C:\\product_lg.gif\"/></p>"
                + "<p><span font=\"subtitle\">Modeling and Simulation of Peer to Peer Systems</span></p>"
                + "<p><a href=\"start\">Go To PeerThing</a></p>" + "</form>";
        tx.setText(text, true, false);
        
        //tx.setImage(1,image);        
        
        //** section 2 /
        // collaborate sections
        Section section1 = tk.createSection(introForm.getBody(),
                Section.TREE_NODE | Section.COMPACT);
        // title 
        section1.setText("What is PeerThing?");
        // contents
        FormText tx2 = tk.createFormText(section1, true);
        // color of contents
        tx2.setColor("body", display.getSystemColor(SWT.COLOR_DARK_BLUE));
        tx2.setHyperlinkSettings(settings);
        tx2.addHyperlinkListener(new IntroHyperlinkListener());
        // text with mark-up
        String text2 = "<form>"
        		+ "<p><span color=\"body\">PeerThing is an application for modeling and simulating peer-to-peer-networks. "
        		+ "Within PeerThing it is possible to compare different architectures. "
        		+ "In order to model a system behaviour this program offers a notation similar to UML."
        		+ " Furthermore you can define a detailled scenario which is used in the simulation. "
        		+ " Data generated during the simulation is stored in a database. "
        		+ "You can manipulate this data with SQL-Statements to prepare it for the visualization."
        		+ "A comparison between different simulation runs is also possible and can be viewed too."
        		+ "Compared to existing solutions PeerThing allows to modify peer-to-peer-systems easily. "
        		+ "After a simulation you can change for example the architecture and see how your "
        		+ "modifications influence the system. As you see a comparison between different " 
        		+ "peer-to-peer-systems is possible within this tool. You can use this feature to "
        		+ "create a new peer-to-peer-system. In addition to that you can use the scenario "
        		+ "defintions to optimize your existing system."
                + "</span></p>"
                + "<p><a href=\"http://se.informatik.uni-oldenburg.de/lehre/projectgroups/pgp2p/\">" 
                + "PeerThing Homepage</a></p></form>";
        tx2.setText(text2, true, false);
        section1.setClient(tx2);
        //** section 2 * * /
        // collaborate sections
        Section section2 = tk.createSection(introForm.getBody(),
                Section.TREE_NODE | Section.COMPACT);
        // title
        section2.setText("License");
        // contents
        FormText tx3 = tk.createFormText(section2, true);
        tx3.setColor("body", display.getSystemColor(SWT.COLOR_DARK_BLUE));
        tx3.setHyperlinkSettings(settings);
        // reaction for hyperlink events
        tx3.addHyperlinkListener(new IntroHyperlinkListener());
        // text with mark-up
        String text3 = "<form>"
                + "<p><span color=\"body\">This product ist copyrighted under the terms of the </span> "
                + "<a href=\"http://www.gnu.de/gpl-ger.html\">" + "GPL</a></p>"
                + "</form>";
        tx3.setText(text3, true, false);
        section2.setClient(tx3);
        
    }

    public void standbyStateChanged(boolean standby) {
    }


}
