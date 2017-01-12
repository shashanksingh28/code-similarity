package edu.asu.ast;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.ReferenceType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shashank Singh (shashank.h.singh@asu.edu) on 9/25/16.
 * This class represents a java function. An instance of this class can be considered as a vector
 * representation of the function, and different vectors can be compared to each other for checking
 * their cosine distance.
 */
public class ASTEnhanced {
    
	//--------- Syntactic features  ----------//

    // [return type, arg1 type, arg2 type, ..]
    ArrayList<String> paramTypes;   
    String returnType;    
    
    HashMap<String, Integer> methodCalls;
    
    HashMap<String, Integer> constants;
    
    HashMap<String, Integer> operators;
    Set<String> annotations;
    HashMap<String, Integer> types;    
    
    // High level concepts we can extract
    Set<String> concepts;
    
    // Exceptions dealt with in this case, both declared and handled
    Set<String> exceptions;

    // ---------- Semantic Features --------- //
    
    // source as is
    String text;
    
    String name;
    String className;
    HashMap<String, Integer> variables;
    
    // JavaDoc documentation string for the method
    String javaDoc;

    // Comments within the method or above it which are not JavaDoc
    String comments;
    
    // ---------- Numeric Features --------- //
    boolean isEmpty;
    int lineCount;
    // This helps determine if the modifier is static
    // If it is static, the scope of a method does not contain class name
    int modifier;

    public ASTEnhanced() {

        this.methodCalls = new HashMap<String, Integer>();
        this.concepts = new HashSet<String>();
        this.comments = null;
        this.paramTypes = new ArrayList<String>();
        this.operators = new HashMap<String, Integer>();
        this.variables = new HashMap<String, Integer>();
        this.constants = new HashMap<String, Integer>();
        this.types = new HashMap<String, Integer>();
        this.exceptions = new HashSet<String>();
        this.annotations = new HashSet<String>();
    }

    public static String cleanDocumentation(String documentation){
        String processed = documentation.replaceAll("(\r\n|\n)"," ");
        // Remove @param and @return because they are common accross
        processed = processed.replaceAll("(@param|@return)", "").replaceAll("[,.]", " ").replaceAll("\\s+", " "); 
        return processed.trim();
    }
    
    /**
     * The standard factory method to get an enhanced AST from a method
     * @param n Method declaration object as per javaparser
     * @return An enhanced AST, which can be converted to a JSON
     */
    public void buildMethodAST(MethodDeclaration n){
        
    	try{
                                    
            this.text = printAndExtractText(n);
            
            // Get all thrown Exceptions
            for(ReferenceType type: n.getThrows()){
            	this.exceptions.add(type.toString());
            	this.concepts.add("ThrowExceptions");
            }
            
            this.name = n.getName();
            Node parent = n.getParentNode();
            if (parent instanceof ClassOrInterfaceDeclaration){
                this.className = ((ClassOrInterfaceDeclaration) parent).getName();
            }
            this.modifier = n.getModifiers();
            this.returnType = n.getType().toString();

            for (Parameter param : n.getParameters()) {
                this.paramTypes.add(param.getType().toString());
                incrementDictCount(this.variables, param.getName());
            }
            
            // Get Annotations
            for(AnnotationExpr annotExpr: n.getAnnotations()){
            	this.annotations.add(annotExpr.getName().toString());
            }
            
            // If JavaDocs present, extract them
            if (n.getJavaDoc() != null){
                this.javaDoc = cleanDocumentation(n.getJavaDoc().getContent());
            }
            
            this.comments = extractContainedComments(n);
            
            // These are in-line comments just before the method definitition
            if (n.getComment() != null){
            	if (this.comments == null){
            		this.comments = "";
            	}
            	this.comments += n.getComment().getContent(); 
            }
            
            // Recursively loop through child nodes and make a dictionary
            this.parseBody(n.getBody());

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void parseBody(BlockStmt block){
        // System.out.println(block);
        if (block == null || block.getChildrenNodes().size() == 0){
        	this.isEmpty = true;
        	return;
        }
        this.lineCount = block.getChildrenNodes().size();
        for (Node childNode : block.getChildrenNodes()){
            parseNode(childNode);
        }
    }

    private void parseNode(Node node) {
    	if (node instanceof VariableDeclaratorId){
    		incrementDictCount(this.variables, ((VariableDeclaratorId) node).getName());
    	} else if (node instanceof MethodCallExpr) {
            this.parseMethodCall((MethodCallExpr) node);
        } else if (node instanceof Expression) {
            this.parseExpr((Expression) node);
        } else if (node instanceof WhileStmt || node instanceof ForeachStmt || node instanceof ForStmt){
            this.concepts.add("Loop");
        } else if (node instanceof IfStmt) {
            this.concepts.add("Decision");
        } else if(node instanceof CatchClause){
        	this.concepts.add("ExceptionHandling");
        	CatchClause catchClause = (CatchClause) node;
        	this.exceptions.add(catchClause.getParam().getType().toString());
        }
    	
        for(Node childNode : node.getChildrenNodes()){
            parseNode(childNode);
        }
        
    }

    private static void incrementDictCount(HashMap<String, Integer> hashMap, String key){
        if (key.length() < 2){
        	// Anything of length 1 is considered trivial.
        	// Most probably variable names like x, i and numbers less than 10
        	return;
        }
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
            incrementDictCount(this.operators, key);
        } else if(expr instanceof BinaryExpr){
            BinaryExpr binaryExpr = (BinaryExpr) expr;
            String key = binaryExpr.getOperator().toString();
            incrementDictCount(this.operators, key);
        } else if(expr instanceof NameExpr){
            // Most likely a variable name
        	String name = ((NameExpr)expr).getName();
            if (name != null){
            	String operandName = ((NameExpr)expr).getName();
            	if (!operandName.startsWith("//") ){
            		incrementDictCount(this.variables, ((NameExpr)expr).getName());
            	}            	
            }            	
        } else if(expr instanceof VariableDeclarationExpr){        	
            int modifiers = ((VariableDeclarationExpr) expr).getModifiers();
            if (modifiers == 16){
            	this.concepts.add("FinalVariables");
            }
        	VariableDeclarationExpr varDecExpr = (VariableDeclarationExpr) expr;
            String leftType = varDecExpr.getType().toString();
            for(Node child : varDecExpr.getChildrenNodes()){
            	if (child instanceof VariableDeclarator){
            		VariableDeclarator varDeclerator = (VariableDeclarator) child;
            		if (varDeclerator.getInit() instanceof ObjectCreationExpr){
            			ObjectCreationExpr creationExpr = (ObjectCreationExpr) varDeclerator.getInit();
                		if(!creationExpr.getType().toString().equals(leftType)){
                			// Left Type is not equal to right, Polymorphism in action
                			this.concepts.add("PolyMorphism");
                		}
            		} else if (varDeclerator.getInit() instanceof CastExpr){
            			this.concepts.add("Casting");
            		}
            	}
            }
            incrementDictCount(this.types, varDecExpr.getType().toString());
        } else if(expr instanceof LiteralExpr){
            // These are primarily integer and string constants
            String stringExpr = ((LiteralExpr) expr).toString().trim();
            if (!stringExpr.startsWith("//")){
            	incrementDictCount(this.constants, stringExpr);
        	}            
        }
    }

    private void parseMethodCall(MethodCallExpr methodCall){
        if (methodCall.getScope() != null){
        	Expression scope = methodCall.getScope();
        	// Sometimes the scope could be System.out but sometimes it could be a variablename
        	// We try to fetch classnames
        	if(scope instanceof FieldAccessExpr){
        		// System.out case
        		incrementDictCount(this.methodCalls, methodCall.getScope() + "." + methodCall.getName());
        	}
        	else{
        		// TODO : check if we can reach to the class here...
        		incrementDictCount(this.methodCalls, methodCall.getScope() + "." + methodCall.getName());
        	}
        	
        }
        else{
        	incrementDictCount(this.methodCalls, methodCall.getName());
        }        
        
        if(this.name.equals(methodCall.getName())){
            this.concepts.add("Recursion");
        }
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
            // extracted += line.replaceAll("\\s+"," ").replaceAll("(\r\n|\n)"," ") + " ";
            extracted += line + "\n";
        }

        return extracted;
    }
    
    public String extractContainedComments(Node node){
    	String comments = "";
    	
    	for(Comment comment : node.getAllContainedComments()){
    		comments += cleanDocumentation(comment.getContent()) + " ";
    	}
    	
    	if(comments.isEmpty()){
    		return null;
    	}
    	
    	return comments;
    }
}
