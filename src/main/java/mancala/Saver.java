package mancala;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import mancala.MancalaGame;

public class Saver {
    private static final String FILE_FOLDER = "assets"; // Adjust the folder name as needed

    public static void saveObject(Serializable toSave, String filename){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(filename)))) {
            oos.writeObject(toSave);
        } catch (IOException e) {
            throw new RuntimeException("Error Saving to file");
        }
    }

    /**
     * 
     * @param filename the name of the file
     * @return loaded object or null depending on outcome
     */
    public static Serializable loadObject(String filename) {

        String filePath = getFilePath(filename);
    
        //Check first if the file exists
        File file = new File(filePath);
        if (!file.exists()) {
            return new MancalaGame();//returns new mancala game on failure
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFilePath(filename)))) {
            Serializable loadedObject = (Serializable) ois.readObject();
            return loadedObject;
        } catch (IOException | ClassNotFoundException e) {
            return new MancalaGame(); //Return a default object if failure to load
        }
    }

    public static void saveUserProfile(UserProfile userProfile, String filename) {
        saveObject(userProfile, filename);
    }

    public static UserProfile loadUserProfile(String filename) {
        File file = new File(getFilePath(filename));
    
        if (!file.exists()) {
            return null;
        }
    
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Serializable loadedObject = (Serializable) ois.readObject();
            return (UserProfile) loadedObject;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
    
    private static String getFilePath(String filename) {
        Path currentPath = Paths.get("");
        Path folderPath = currentPath.toAbsolutePath().resolve(FILE_FOLDER);

        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                throw new RuntimeException("Error creating folder: " + FILE_FOLDER);
            }
        }

        Path filePath = folderPath.resolve(filename);
        return filePath.toString();
    }

}
