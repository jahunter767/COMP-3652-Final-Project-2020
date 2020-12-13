/* Specification for ArithExp tokens New vers */

// user customisations
import java_cup.runtime.*;

// Jlex directives
    
%%

%cup
%public

%class Lexer

%type java_cup.runtime.Symbol

%throws TokenException

%eofval{
	return new Symbol(sym.EOF);
%eofval}

%eofclose false

%char
%column
%line

%{
    public int getChar() {
	return (int)yychar + 1;
    }

    public int getColumn() {
    	return (int)yycolumn + 1;
    }

    public int getLine() {
	return (int)yyline + 1;
    }

    public String getText() {
	return yytext();
    }
%}

nl = [\n\r]

cc = ([\b\f]|{nl})

ws = {cc}|[\t ]

alpha = [a-zA-Z_]

alphanum = {alpha}|[0-9]

%%

<YYINITIAL>	{nl}	{//skip newline, but reset char counter
			 yychar = 0;
			}
<YYINITIAL>	{ws}	{
			 // skip whitespace
			}
<YYINITIAL>	"+"	{return new Symbol(sym.PLUS);}
<YYINITIAL>	"-"	{return new Symbol(sym.MINUS);}
<YYINITIAL>	"*"	{return new Symbol(sym.MUL);}
<YYINITIAL>	"/"	{return new Symbol(sym.DIV);}
<YYINITIAL>	"%"	{return new Symbol(sym.MOD);}

<YYINITIAL>	"<"	{return new Symbol(sym.LESSTHAN);}
<YYINITIAL>	"<="	{return new Symbol(sym.LESSTHANEQUAL);}
<YYINITIAL>	"=="	{return new Symbol(sym.EQUAL);}
<YYINITIAL>	"!="	{return new Symbol(sym.NOTEQUAL);}
<YYINITIAL>	">="	{return new Symbol(sym.GREATERTHANEQUAL);}
<YYINITIAL>	">"	{return new Symbol(sym.GREATERTHAN);}


<YYINITIAL>	"not"	{return new Symbol(sym.NOT);}
<YYINITIAL>	"and"	{return new Symbol(sym.AND);}
<YYINITIAL>	"or"	{return new Symbol(sym.OR);}

<YYINITIAL>	"if"	{return new Symbol(sym.IF);}
<YYINITIAL>	"then"	{return new Symbol(sym.THEN);}
<YYINITIAL>	"else"	{return new Symbol(sym.ELSE);}

<YYINITIAL>	":="	{return new Symbol(sym.ASSIGN);}
<YYINITIAL>	"("	{return new Symbol(sym.LPAREN);}
<YYINITIAL>	")"	{return new Symbol(sym.RPAREN);}
<YYINITIAL>	";"	{return new Symbol(sym.SEMI);}
<YYINITIAL>	","	{return new Symbol(sym.COMMA);}
<YYINITIAL>	"="	{return new Symbol(sym.DEFINE);}
<YYINITIAL>	"{"	{return new Symbol(sym.LBRACE);}
<YYINITIAL>	"}"	{return new Symbol(sym.RBRACE);}
<YYINITIAL>	"fun"	{return new Symbol(sym.FUN);}


<YYINITIAL>    [0-9]+ {
	       // INTEGER
	       return new Symbol(sym.INT, 
				 new Integer(yytext()));
		}

<YYINITIAL>    {alpha}{alphanum}* {
	       // VAR
	       return new Symbol(sym.VAR, yytext());
		}

<YYINITIAL>    \S		{ // error situation
	       String msg = String.format("Unrecognised Token: %s", yytext());
	       throw new TokenException(msg);
	       }