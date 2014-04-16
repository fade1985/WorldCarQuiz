package com.android.worldcarquiz.cache;

import java.io.File;
import android.content.Context;
  
public class FileCache {
  
    private File cacheDir;
  
    public FileCache(Context context){
        //Find the dir to save cached images
        cacheDir=new File(context.getExternalCacheDir(),"TempImages");
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
  
    public File getFile(String url){
        String filename=String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
  
    }
  
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }
  
}