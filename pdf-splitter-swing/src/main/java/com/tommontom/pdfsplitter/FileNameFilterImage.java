package com.tommontom.pdfsplitter;

import java.io.*;
public class FileNameFilterImage implements FileFilter
{
  private final String[] okFileExtensions = 
    new String[] {"jpg", "gif", "\\"};
 
  public boolean accept(File file)
  {
    for (String extension : okFileExtensions)
    {
      if (file.getName().toLowerCase().endsWith(extension))
      {
        return true;
      }
    }
    return false;
  }
}