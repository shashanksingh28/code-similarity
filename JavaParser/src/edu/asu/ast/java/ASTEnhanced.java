package edu.asu.ast.java;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shashank on 9/25/16.
 * This class represents a java function. An instance of this class can be considered as a vector
 * representation of the function, and different vectors can be compared to each other for checking
 * their cosine distance.
 */
public class ASTEnhanced {

    //--------- Natural Language Elements, to be treated semantically ----------//

    String Name;
    String ClassName;
    // source as is
    String Text;

    // This helps determine if the modifier is static
    // If it is static, the scope of a method does not contain class name
    int Modifiers;

    // Javadoc documentation string for the method
    String JavaDoc;

    // Comments within the method or above it which are not JavaDoc
    String Comments;

    // List of all methods called (with the scope)
    Set<String> MethodNames;

    // List of all variable names and constants
    Set<String> Operands;

    Set<String> Concepts;

    //--------- Syntactical Elements, to be treated  ----------//

    // [return type, arg1 type, arg2 type, ..]
    ArrayList<String> Signature;

    // Operators include all kinds of boolen, mathematical and other operations
    Set<String> Operators;

    // Count of types used, like int, string, etc
    HashMap<String, Integer> Types;

    ASTEnhanced() {

        this.MethodNames = new HashSet<String>();
        this.Concepts = new HashSet<String>();
        this.Comments = "";
        this.Signature = new ArrayList<String>();
        this.Operators = new HashSet<String>();
        this.Operands = new HashSet<String>();
        this.Types = new HashMap<String, Integer>();
    }

    public static String cleanDocumentation(String documentation){
        return documentation.replaceAll("\\s+"," ").replaceAll("(\r\n|\n)"," ").trim();
    }
    
    /**
     * The standard factory method to get an enhanced AST from a method
     * @param n Method declaration object as per javaparser
     * @return An enhanced AST, which can be converted to a JSON
     */
    public void buildMethodAST(MethodDeclaration n){
        try{
            Pattern commentPattern = Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)");
            Matcher commentMatcher;
            
            this.Text = printAndExtractText(n);
            this.Name = n.getName();
            Node parent = n.getParentNode();
            if (parent instanceof ClassOrInterfaceDeclaration){
                this.ClassName = ((ClassOrInterfaceDeclaration) parent).getName();
            }
            this.Modifiers = n.getModifiers();
            this.Signature.add(n.getType().toString());

            for (Parameter param : n.getParameters()) {
                this.Signature.add(param.getType().toString());
                this.Operands.add(param.getName());
            }

            if (n.getJavaDoc() != null){
                this.JavaDoc = cleanDocumentation(n.getJavaDoc().getContent());
                               
                // Get all comments from the body
                commentMatcher = commentPattern.matcher(n.getBody().toString());
                while(commentMatcher.find()){
                    this.Comments += commentMatcher.group() + " " ;
                }

            }
            else{
           	
            	// Since we don't have Javadoc, comments might be before the body too
                commentMatcher = commentPattern.matcher(n.toString());
                while(commentMatcher.find()){
                    this.Comments += commentMatcher.group() + " ";
                }
            }

            // Recursivelly loop through child nodes and make a dictionary
            this.parseBody(n.getBody());

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void parseBody(BlockStmt block){
        // System.out.println(block);
        if (block == null) return;
        for (Node childNode : block.getChildrenNodes()){
            parseNode(childNode);
        }
    }

    private void parseNode(Node node) {
        if (node instanceof MethodCallExpr) {
            this.parseMethodCall((MethodCallExpr) node);
        } else if (node instanceof Expression) {
            this.parseExpr((Expression) node);
        } else if (node instanceof WhileStmt || node instanceof ForeachStmt || node instanceof ForStmt){
            this.Concepts.add("Loop");
        } else if (node instanceof IfStmt) {
            this.Concepts.add("Decision");
        }
        for(Node childNode : node.getChildrenNodes()){
            parseNode(childNode);
        }
    }

    private static void incrementDictCount(HashMap<String, Integer> hashMap, String key){
        if (hashMap.containsKey(key)){
            int count = hashMap.get(key);
            hashMap.put(key, count + 1);
        }
        else{
            hashMap.put(key, 1);
        }
    }

    private void parseExpr(Expression expr){
        if(expr instanceof UnaryExpr){
            UnaryExpr unaryExpr = (UnaryExpr)expr;
            String key = unaryExpr.getOperator().toString();
            this.Operators.add(key);
        } else if(expr instanceof BinaryExpr){
            BinaryExpr binaryExpr = (BinaryExpr) expr;
            String key = binaryExpr.getOperator().toString();
            this.Operators.add(key);
        } else if(expr instanceof NameExpr){
            // Most likely a variable name
            this.Operands.add(((NameExpr)expr).getName());
        } else if(expr instanceof VariableDeclarationExpr){
            VariableDeclarationExpr varDecExpr = (VariableDeclarationExpr) expr;
            incrementDictCount(this.Types, varDecExpr.getType().toString());
        } else if(expr instanceof LiteralExpr){
            // These are primarily integer and string constants
            LiteralExpr literalExpr = (LiteralExpr) expr;
            this.Operands.add(literalExpr.toString());
        }

    }

    private void parseMethodCall(MethodCallExpr expr){
        String currentMethod, parsedMethod;
        if(this.Modifiers != 9){
            // This is not a static method so normal scope
            currentMethod = expr.getScope() + "." + expr.getName();
            parsedMethod = this.ClassName + "." + this.Name;
        }
        else{
            // if static, do not use scope
            currentMethod = expr.getName();
            parsedMethod = this.Name;
        }

        if(currentMethod.equals(parsedMethod)){
            this.Concepts.add("Recursion");
        }

        this.MethodNames.add(currentMethod);
    }
    
    /**
     * @return JSON representation of Enhanced - AST
     */
    public String toJSON(){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }

    public String printAndExtractText(MethodDeclaration n){
        String content = n.toString();
    	if (n.getJavaDoc() != null){
        	// JavaDoc is double counted, so ensure
    		String javaDocString = n.getJavaDoc().toString();
    		content = content.substring(content.indexOf(javaDocString) + javaDocString.length());
        }
    	
    	String[] lines = content.split("\n");
        String extracted = "";
        for(String line : lines){
            System.out.println("# " + line);
            extracted += line.replaceAll("\\s+"," ").replaceAll("(\r\n|\n)"," ") + " ";
        }

        return extracted;

    }
}
