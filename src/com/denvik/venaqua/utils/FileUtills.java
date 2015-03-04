package com.denvik.venaqua.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Created by DV21 on 25-02-2015.
 */
public class FileUtills {

    public static void copy( File src , File dest ) throws IOException {
        if( !src.exists() )
            throw new FileNotFoundException( "Source File '" + src.getPath() + "' is not exists." );
        else if( !dest.exists() )
            throw new FileNotFoundException( "Destination File '" + dest.getPath() + "' is not exists." );
        else {
            if( src.isDirectory() && dest.isDirectory() )
                FileUtils.copyDirectory( src , dest );
            else if( src.isFile() && dest.isFile() )
                FileUtils.copyFile( src , dest );
            else if( src.isFile() && dest.isDirectory() )
                FileUtils.copyFileToDirectory( src , dest );
        }

    }
    public static void copy( File src , File dest , boolean preserveFileDate ) throws IOException {
        if( !src.exists() )
            throw new FileNotFoundException( "Source File '" + src.getPath() + "' is not exists." );
        else if( !dest.exists() )
            throw new FileNotFoundException( "Destination File '" + dest.getPath() + "' is not exists." );
        else {
            if (src.isDirectory() && dest.isDirectory())
                FileUtils.copyDirectory(src, dest, preserveFileDate);
            else if (src.isFile() && dest.isFile())
                FileUtils.copyFile(src, dest, preserveFileDate);
            else if (src.isFile() && dest.isDirectory())
                FileUtils.copyFileToDirectory(src, dest, preserveFileDate);
        }
    }
    public static void copy( File src , File dest , FileFilter filter ) throws IOException {
        if( src.isDirectory() && dest.isDirectory() )
            FileUtils.copyDirectory( src , dest , filter );
    }
    public static void copy( File src , File dest , FileFilter filter , boolean preserveFileDate ) throws IOException {
        if( src.isDirectory() && dest.isDirectory() )
            FileUtils.copyDirectory( src , dest , filter , preserveFileDate );
    }
    public static void copy( File src , OutputStream outputStream ) throws IOException {
        if( src.isFile() )
            FileUtils.copyFile( src , outputStream );
        else
            throw new IOException( "Input File ' " + src.getPath() + " ' is not exists." );
    }
    public static void copy( InputStream inputStream , File dest ) throws IOException {
        if( dest.isFile() )
            FileUtils.copyInputStreamToFile( inputStream , dest );
        else
            throw new IOException( "Destination File ' " + dest.getPath() + " ' is not a valid File." );
    }
    public static void copy( URL srcURL , File dest ) throws IOException {
        FileUtils.copyURLToFile( srcURL , dest );
    }
    public static void copy( URL srcURL , File dest , int connectTimeout , int readTimeout ) throws IOException {
        FileUtils.copyURLToFile( srcURL , dest , connectTimeout , readTimeout );
    }

    public static Checksum calculateChecksum( File file ) throws IOException {
        Checksum checksum = new CRC32();
        return FileUtils.checksum( file , checksum );
    }

    public static void removeDirectoryContents( File dir ) throws IOException {
        if( dir.isDirectory() )
            FileUtils.cleanDirectory( dir );
        else
            throw new IllegalArgumentException( "Input file path dir-'" + dir.getPath() + "' is not a directory." );
    }

    public static File[] convertToFileArray( List<File> fileList ) throws IOException {
        File[] fileArray = null;
        try {
            if( fileList != null )
                if( fileList.size() > 0 )
                    fileArray = FileUtils.convertFileCollectionToFileArray( fileList );

        } catch ( Exception e ) {
            throw new IOException( e );
        }
        return fileArray;
    }
}
