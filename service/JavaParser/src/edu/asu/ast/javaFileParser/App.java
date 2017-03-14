package edu.asu.ast.javaFileParser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import edu.asu.ast.ASTEnhanced;

import java.io.*;
import java.util.ArrayList;

/**
 * App that parses an input java file methods into Enhanced AST
 */
public class App 
{

    private static class MethodVisitor extends VoidVisitorAdapter {
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this
            // CompilationUnit, including inner class method
            super.visit(n, arg);
            ASTEnhanced ast = new ASTEnhanced();
            ast.buildMethodAST(n);
            System.out.println(ast.toJSON());
        }
        
        @Override
        public void visit(ConstructorDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this
            // CompilationUnit, including inner class method
            super.visit(n, arg);
            ASTEnhanced ast = new ASTEnhanced();
            ast.buildConstructorAST(n);
            System.out.println(ast.toJSON());
        }        
    }

    private static ArrayList<File> getJavaFilesFromDir(File path){
    	File [] allFiles = path.listFiles();
    	ArrayList<File> javaFiles = new ArrayList<File>();
    	for(File file : allFiles){
    		if (!file.isDirectory()){
    			if(file.getName().endsWith(".java")){
    				javaFiles.add(file);
    			}
    		}
    		else{
    			javaFiles.addAll(getJavaFilesFromDir(file));
    		}
    	}
    	return javaFiles;
    }
    
    public static void main( String[] args )
    {
        CompilationUnit cu;
        MethodVisitor mv = new MethodVisitor();
        
        try{
            if (args.length == 0){
                System.out.println("Provide a directory with java files");
                System.exit(0);
            }

            File folder = new File(args[0]);
            ArrayList<File> javaFiles = getJavaFilesFromDir(folder);

            for(File file : javaFiles){
            	// System.out.println(file.getAbsolutePath());
                try{
                	cu = JavaParser.parse(file);
                	mv.visit(cu, null);
                }catch (ParseException e) {
                    e.printStackTrace();
                }                 
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

