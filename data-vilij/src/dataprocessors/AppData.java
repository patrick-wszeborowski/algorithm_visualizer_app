// Patrick Wszeborowski
// SBU ID: 111007547
// CSE 219 Spring 2018

package dataprocessors;

import dataprocessors.TSDProcessor.InvalidDataNameException;
import java.io.IOException;
import ui.AppUI;
import vilij.components.DataComponent;
import vilij.templates.ApplicationTemplate;

import java.nio.file.Path;
import java.util.Scanner;
import vilij.components.Dialog;
import static vilij.settings.InitializationParams.LOAD_ERROR_TITLE;

/**
 * This is the concrete application-specific implementation of the data component defined by the Vilij framework.
 *
 * @see DataComponent
 */
public class AppData implements DataComponent {

    private TSDProcessor        processor;
    private ApplicationTemplate applicationTemplate;

    public AppData(ApplicationTemplate applicationTemplate) {
        this.processor = new TSDProcessor();
        this.applicationTemplate = applicationTemplate;
    }
    
    public TSDProcessor getProcessor() {
        return processor;
    }
    
    @Override
    public void loadData(Path dataFilePath) {
        ((AppUI) applicationTemplate.getUIComponent()).setTextArea("");
        int lineCounter = 0;
         try {
            Scanner scan = new Scanner(dataFilePath.toAbsolutePath());
            String fullText = "";
                while (scan.hasNextLine()) {
                    String nextLine = scan.nextLine();
                    fullText += nextLine;
                    if (scan.hasNextLine())
                        fullText += "\n";
                    processor.processString(nextLine);
                    lineCounter++;
                }
                if (lineCounter > 10) {
                    applicationTemplate.getDialog(Dialog.DialogType.ERROR).show(applicationTemplate.manager.getPropertyValue(LOAD_ERROR_TITLE.name()),
                            "Loaded data consists of " + lineCounter + " lines. Showing only the first 10 in the text area.");
                }
                ((AppUI) applicationTemplate.getUIComponent()).setTextArea(fullText);
                ((AppUI) applicationTemplate.getUIComponent()).addInfo(lineCounter, processor.getDataLabels().size(), processor.getDataLabels().values().toString(), dataFilePath.toString());
        }
        catch (InvalidDataNameException e) {
            applicationTemplate.getDialog(Dialog.DialogType.ERROR).show(applicationTemplate.manager.getPropertyValue(LOAD_ERROR_TITLE.name()), e.getMessage()
                    + " at line number: " + lineCounter);
            ((AppUI) applicationTemplate.getUIComponent()).setSaveButton(true);
        }
        catch (Exception e) {
            applicationTemplate.getDialog(Dialog.DialogType.ERROR).show(applicationTemplate.manager.getPropertyValue(LOAD_ERROR_TITLE.name()), e.getMessage());
            ((AppUI) applicationTemplate.getUIComponent()).setSaveButton(true);
        }
    }
    
    public void loadData(String dataString) throws Exception {
        int lineCounter = 0;
        try {
            Scanner scan = new Scanner(dataString);
            while (scan.hasNextLine()) {
                processor.processString(scan.nextLine()); 
                lineCounter++;
            }
            ((AppUI) applicationTemplate.getUIComponent()).addInfo(lineCounter, processor.getDataLabels().size(), processor.getDataLabels().values().toString());
        }
        catch (InvalidDataNameException e) {
            applicationTemplate.getDialog(Dialog.DialogType.ERROR).show(applicationTemplate.manager.getPropertyValue(LOAD_ERROR_TITLE.name()), e.getMessage()
                    + " at line number: " + lineCounter);
            ((AppUI) applicationTemplate.getUIComponent()).setSaveButton(true);
        }
        catch (Exception e) {
            applicationTemplate.getDialog(Dialog.DialogType.ERROR).show(applicationTemplate.manager.getPropertyValue(LOAD_ERROR_TITLE.name()), e.getMessage());
            ((AppUI) applicationTemplate.getUIComponent()).setSaveButton(true);
        }
    }

    @Override
    public void saveData(Path dataFilePath) {
        int lineCounter = 0;
        try {
            Scanner scan = new Scanner(dataFilePath.toAbsolutePath());
            while (scan.hasNextLine()) {
                processor.processString(scan.nextLine());
                lineCounter++;
            }
        }
        catch (InvalidDataNameException e) {
            applicationTemplate.getDialog(Dialog.DialogType.ERROR).show(applicationTemplate.manager.getPropertyValue(LOAD_ERROR_TITLE.name()), e.getMessage()
                    + " at line number: " + lineCounter);
            ((AppUI) applicationTemplate.getUIComponent()).setSaveButton(true);
        } catch (IOException ex) {
          
        } catch (Exception ex) {
            
        }
    }

    @Override
    public void clear() {
        processor.clear();
    }

    public void displayData() {
        processor.toChartData(((AppUI) applicationTemplate.getUIComponent()).getChart());
    }
}
