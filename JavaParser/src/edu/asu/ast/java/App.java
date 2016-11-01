package edu.asu.ast.java;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.*;

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
            System.out.println();
            System.out.println();
        }
    }

    public static void main( String[] args )
    {
        CompilationUnit cu;
        MethodVisitor mv = new MethodVisitor();
        try{
            if (args.length == 0){
                System.out.println("Provide a directory with java files");
            }

            File folder = new File(args[0]);
            File[] javaFiles = folder.listFiles(new FilenameFilter() {
                public boolean accept(File file, String s) {
                    if (s.endsWith(".java"))
                        return true;
                    return false;
                }
            });

            for(File file : javaFiles){
                cu = JavaParser.parse(file);
                mv.visit(cu, null);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printFileContents(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println("# " + line);
        }
    }
}

