package pe.kr.crasy.dunningassignments.Edit;

import java.util.Date;

import io.realm.Realm;
import pe.kr.crasy.dunningassignments.database.Assignments;

/**
 *
 * Created by maybe on 16. 12. 29.
 */

public class EditPresenter {
    private Realm realm;
    private PView view;
    private EditModel model;
    public EditPresenter(PView view){
        this.view = view;
        this.model = new EditModel();
        this.realm = Realm.getDefaultInstance();
    }

    public void onTitleUpdate(String title){
        model.setTitle(title);
    }

    public void onLocationUpdate(String location){
        model.setLocation(location);
    }

    public void onMetaDataUpdate(String metaData){
        model.setMetaData(metaData);
    }

    public void closeActivity(){
        if(!isValid()){
            view.onCloseActivity(false);
            return;
        }
        realm.executeTransaction(realm1 -> {
            Assignments assignments = realm1.createObject(Assignments.class);
            assignments.setTitle(model.getTitle());
            assignments.setLocation(model.getLocation());
            assignments.setStartLine(model.getStartLine());
            assignments.setDeadLine(model.getDeadLine());
            assignments.setMetaData(model.getMetaData());
        });
        realm.close();
        view.onCloseActivity(true);
    }

    public void onDateUpdate(Date date, String title){
        if(title.equals("startLine")){
            model.setStartLine(date);
            view.onStartLineText(date);
        } else {
            model.setDeadLine(date);
            view.onDeadLineText(date);
        }
    }

    public boolean isValid(){
        return model.getTitle() != null && model.getLocation() != null && model.getDeadLine() != null;
    }

    public interface PView {
        void onCloseActivity(boolean isSaved);
        void onStartLineText(Date date);
        void onDeadLineText(Date date);
    }
}
