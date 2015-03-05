package com.denvik.venaqua;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.denvik.venaqua.mysql.dbConfig;
import com.denvik.venaqua.utils.FileUtills;


class mainApp {
	
	public static void main( String args[] ) 
	{
		process( "G:/Test/ytp" ,  "G:/Test/done" );
	}
	
	private static void process( String ytpDirPath , String doneDirPath ) {
        try {
            File ytpDir = new File( ytpDirPath );
            File doneDir = new File( doneDirPath );

            if( ytpDir.isDirectory() ) {

                if( !doneDir.exists() )
                    doneDir.mkdirs();
                else
                    if( !doneDir.isDirectory() )
                        throw new Exception( "Not a valid Done directory..." );

                System.out.println( "Initial checkings completed..." );

                File[] ytpContents = ytpDir.listFiles();
                if( ytpContents.length > 0 ) {
                    for( int i=0; i<ytpContents.length; i++ ) {
                        File file = ytpContents[i];
                        System.out.println( "processing file : " + file.getName() );

                        FileUtills.copy( file , doneDir );
                        System.out.println( "File -- " + file.getName() + " copied to done directory...." );

                        file.delete();
                    }
                }
            } else {
                throw new Exception( "Not a valid YTP directory..." );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

}