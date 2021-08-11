// Patrick Wszeborowski
// SBU ID: 111007547
// CSE 219 Spring 2018

package actions;

import java.io.File;
import java.io.FileWriter;
import vilij.components.ActionComponent;
import vilij.templates.ApplicationTemplate;

import java.io.IOException;
import java.nio.file.Path;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;
import static settings.AppPropertyTypes.DATA_FILE_EXT;
import static settings.AppPropertyTypes.DATA_FILE_EXT_DESC;
import static settings.AppPropertyTypes.DATA_RESOURCE_PATH;
import static settings.AppPropertyTypes.SAVE_UNSAVED_WORK;
import static settings.AppPropertyTypes.SAVE_UNSAVED_WORK_TITLE;
import ui.AppUI;
import vilij.components.ConfirmationDialog;
import vilij.components.ConfirmationDialog.Option;
import vilij.components.Dialog;
import static vilij.settings.PropertyTypes.SAVE_ERROR_MSG;
import static vilij.settings.PropertyTypes.SAVE_ERROR_TITLE;
import static vilij.settings.PropertyTypes.SAVE_WORK_TITLE;

/**
 * This is the concrete implementation of the action handlers required by the application.
 *
 * @author Ritwik Banerjee
 */
public final class AppActions implements ActionComponent {
    
    File currentFile;
    /** The application to which this class of actions belongs. */
    private ApplicationTemplate applicationTemplate;

    /** Path to the data file currently active. */
    Path dataFilePath;

    public AppActions(ApplicationTemplate applicationTemplate) {
        this.applicationTemplate = applicationTemplate;
    }

    @Override
    public void handleNewRequest() {
        if (!((AppUI) applicationTemplate.getUIComponent()).getText().equals("")) {
            if (promptToSave() == true) {
                this.applicationTemplate.getUIComponent().clear();
            }
        }
        ((AppUI) applicationTemplate.getUIComponent()).setNewButtonCheck(false);
        ((AppUI) applicationTemplate.getUIComponent()).setVisibility();
    }

    @Override
    public void handleSaveRequest() {
        if (((AppUI) applicationTemplate.getUIComponent()).getNewButtonCheck() == false) {
            File file = new File(applicationTemplate.manager.getPropertyValue(DATA_RESOURCE_PATH.name()));
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(file);
            fileChooser.setTitle(applicationTemplate.manager.getPropertyValue(SAVE_WORK_TITLE.name()));
            fileChooser.getExtensionFilters().add(new ExtensionFilter(applicationTemplate.manager.getPropertyValue(DATA_FILE_EXT_DESC.name()),
                applicationTemplate.manager.getPropertyValue(DATA_FILE_EXT.name())));
            file = fileChooser.showSaveDialog(applicationTemplate.getUIComponent().getPrimaryWindow());
            if (file != null) {
                currentFile = file;
                ((AppUI) applicationTemplate.getUIComponent()).setNewButtonCheck(true);
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(((AppUI) applicationTemplate.getUIComponent()).getText());
                }
                catch (Exception e) {
                    applicationTemplate.getDialog(Dialog.DialogType.ERROR).show(applicationTemplate.manager.getPropertyValue(SAVE_ERROR_TITLE.name()), 
                        applicationTemplate.manager.getPropertyValue(SAVE_ERROR_MSG.name() + file.getName()));
                }
            }
        }
        else {
            try (FileWriter fileWriter = new FileWriter(currentFile)) {
                fileWriter.write(((AppUI) applicationTemplate.getUIComponent()).getText());
            }
            catch (Exception e) {
                applicationTemplate.getDialog(Dialog.DialogType.ERROR).show(applicationTemplate.manager.getPropertyValue(SAVE_ERROR_TITLE.name()), 
                    applicationTemplate.manager.getPropertyValue(SAVE_ERROR_MSG.name() + currentFile.getName()));
            }
        }
        ((AppUI) applicationTemplate.getUIComponent()).setSaveButton(true);
    }

    @Override
    public void handleLoadRequest() {
        ((AppUI) applicationTemplate.getUIComponent()).disableScrollBar();
        ((AppUI) applicationTemplate.getUIComponent()).clear();
        File file = new File(applicationTemplate.manager.getPropertyValue(DATA_RESOURCE_PATH.name()));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file);
        fileChooser.setTitle("Load");
        fileChooser.getExtensionFilters().add(new ExtensionFilter(applicationTemplate.manager.getPropertyValue(DATA_FILE_EXT_DESC.name()),
            applicationTemplate.manager.getPropertyValue(DATA_FILE_EXT.name())));
        file = fileChooser.showOpenDialog(applicationTemplate.getUIComponent().getPrimaryWindow());
        if (file != null) {
            applicationTemplate.getDataComponent().loadData(file.toPath());
            ((AppUI) applicationTemplate.getUIComponent()).readOnly();
            ((AppUI) applicationTemplate.getUIComponent()).getCheckBox().setSelected(true);
            ((AppUI) applicationTemplate.getUIComponent()).setVisibility();
        }
    }

    @Override
    public void handleExitRequest() {
        if (!((AppUI) applicationTemplate.getUIComponent()).getText().equals(""))
                if (promptToSave() == true) {
                this.applicationTemplate.getUIComponent().clear();
            }
        applicationTemplate.getUIComponent().getPrimaryWindow().close();
    }

    @Override
    public void handlePrintRequest() {
        // TODO: NOT A PART OF HW 1
    }

    public void handleScreenshotRequest() throws IOException {
        WritableImage image = ((AppUI) applicationTemplate.getUIComponent()).getChart().snapshot(new SnapshotParameters(), null);
        File file = new File(applicationTemplate.manager.getPropertyValue(DATA_RESOURCE_PATH.name()));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file);
        fileChooser.setTitle(applicationTemplate.manager.getPropertyValue(SAVE_WORK_TITLE.name()));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG File", "*.png"));
        file = fileChooser.showSaveDialog(applicationTemplate.getUIComponent().getPrimaryWindow());
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            
        }  
    }

    /**
     * This helper method verifies that the user really wants to save their unsaved work, which they might not want to
     * do. The user will be presented with three options:
     * <ol>
     * <li><code>yes</code>, indicating that the user wants to save the work and continue with the action,</li>
     * <li><code>no</code>, indicating that the user wants to continue with the action without saving the work, and</li>
     * <li><code>cancel</code>, to indicate that the user does not want to continue with the action, but also does not
     * want to save the work at this point.</li>
     * </ol>
     *
     * @return <code>false</code> if the user presses the <i>cancel</i>, and <code>true</code> otherwise.
     */
    private boolean promptToSave() {
        // TODO for homework 1
        ConfirmationDialog d = ConfirmationDialog.getDialog();
        applicationTemplate.getDialog(Dialog.DialogType.CONFIRMATION).show(applicationTemplate.manager.getPropertyValue(SAVE_UNSAVED_WORK_TITLE.name()),
                applicationTemplate.manager.getPropertyValue(SAVE_UNSAVED_WORK.name()));
        Option o = d.getSelectedOption();
        if (o == Option.YES) {
            handleSaveRequest();
            return true;
        }
        else if (o == Option.NO) {
            return true;
        }
        else {
            return false;
        }
    }
}
