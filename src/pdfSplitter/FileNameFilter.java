package pdfSplitter;

import java.io.*;
public class FileNameFilter implements FileFilter
{
  private final String[] okFileExtensions = 
    new String[] {"pdf", "\\"};
 
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