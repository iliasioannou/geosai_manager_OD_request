package it.planetek.marinecmems.managerod.processor.utils;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Francesco Bruni on 7/5/17.
 */
public class Zipper {

    /***
     * Zip source file/folder in the @param{destinationFolder} and give it a random name
     * Please us absolute paths to be sure everythings is not referred to classpath.
     * @param sourceFile the file/folder to be zipped
     * @param destinationFolder the folder whose produced zip will be placed in
     * @return the zipped file destination
     */
    public String zipFileWithRandomName(String sourceFile, String destinationFolder){
            String destPath = destinationFolder
                    .concat(new BigInteger(130,  new SecureRandom()).toString(16))
                    .concat(".zip");
            ZipUtil.pack(new File(sourceFile),
                    new File(destPath));
            return destPath;
        }
}
