package edu.asu.ast.javaMethodParser;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import edu.asu.ast.ASTEnhanced;

/**
 * App that parses an input java method into Enhanced AST
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
        	
            BufferedReader bIn = new BufferedReader(new InputStreamReader(System.in));
            String input = "class dummy{\n";
            String s;
            while ((s = bIn.readLine()) != null)
            {
            	input += s + "\n";
            }
            input = input + "\n}";

        	cu = JavaParser.parse(new StringReader(input));
        	mv.visit(cu, null);            
        } catch (Exception e) {
        	e.printStackTrace();
        	System.exit(1);
        }
    }

}

