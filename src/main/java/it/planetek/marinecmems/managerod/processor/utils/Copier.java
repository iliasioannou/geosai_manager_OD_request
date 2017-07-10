package it.planetek.marinecmems.managerod.processor.utils;

import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/10/17.
 */
@Service("Copier")
public class Copier {

    /***
     * Copy file in a folder.
     * @param sourceFile the folder to be copied
     * @param destFolder the folder whose source folder will be placed in
     * @return the destFolder path
     */
    public String copyFileInFolder(String sourceFile, String destFolder) throws IOException {
        FileUtils.copyFileToDirectory(new File(sourceFile), new File(destFolder));
        return destFolder;
    }
}
