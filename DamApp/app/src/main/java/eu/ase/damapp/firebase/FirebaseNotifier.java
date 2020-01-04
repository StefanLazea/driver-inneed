package eu.ase.damapp.firebase;

import java.util.List;

import eu.ase.damapp.util.Form;

public interface FirebaseNotifier {
    void notifyCoachesChanges(List<Form> results);

}
