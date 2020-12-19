/* Specification for ArithExp tokens */

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
    	return yycolumn + 1;
    }

    public int getLine() {
	return yyline + 1;
    }

    public String getText() {
	return yytext();
    }
%}

%debug

%state STRING
%state CHAR_ESCAPE

nl = [\n\r]

cc = ([\b\f]|{nl})

ws = {cc}|[\t ]

alpha = [a-zA-Z_]

alphanum = {alpha}|[0-9] 

special = [\?!-\*/%\^&\+|~@] | {ws}

allChar = {alphanum}|{special}

reserved = [substr,size]

hex = [0-9A-Fa-f]

%%

<YYINITIAL>	{nl}	{//skip newline, but reset char counter
			 yychar = 0;
			}
<YYINITIAL>	{ws}	{
			 // skip whitespace
			}

<YYINITIAL>	"and"	{return new Symbol(sym.AND);}
<YYINITIAL>	"or"	{return new Symbol(sym.OR);}
<YYINITIAL>	"not"	{return new Symbol(sym.NOT);}

<YYINITIAL>	" < "	{return new Symbol(sym.LESS);}
<YYINITIAL>	" <= "	{return new Symbol(sym.LESSEQ);}
<YYINITIAL>	" == "	{return new Symbol(sym.EQUAL);}
<YYINITIAL>	" >= "	{return new Symbol(sym.GREATEREQ);}
<YYINITIAL>	" > "	{return new Symbol(sym.GREATER);}
<YYINITIAL>	" != "	{return new Symbol(sym.NEQUAL);}

<YYINITIAL>	" + "	{return new Symbol(sym.PLUS);}
<YYINITIAL>	" - "	{return new Symbol(sym.MINUS);}
<YYINITIAL>	" * "	{return new Symbol(sym.MUL);}
<YYINITIAL>	" / "	{return new Symbol(sym.DIV);}
<YYINITIAL>	" % "	{return new Symbol(sym.MOD);}
<YYINITIAL>	" ^ "	{return new Symbol(sym.EXP);}

<YYINITIAL>	" & "	{return new Symbol(sym.BIT_AND);}
<YYINITIAL>	" | "	{return new Symbol(sym.BIT_OR);}
<YYINITIAL>	" ~ "	{return new Symbol(sym.BIT_NOT);}

<YYINITIAL>	" @ "	{return new Symbol(sym.LSTCONCAT);}

<YYINITIAL>	":="	{return new Symbol(sym.DEFINE);}
<YYINITIAL>	"="	{return new Symbol(sym.ASSIGN);}
<YYINITIAL>	";"	{return new Symbol(sym.SEMI);}
<YYINITIAL>	"{"	{return new Symbol(sym.LBRACE);}
<YYINITIAL>	"}"	{return new Symbol(sym.RBRACE);}
<YYINITIAL>	"("	{return new Symbol(sym.LPAREN);}
<YYINITIAL>	")"	{return new Symbol(sym.RPAREN);}
<YYINITIAL>	","	{return new Symbol(sym.COMMA);}
<YYINITIAL>	"["	{return new Symbol(sym.LSQPAREN);}
<YYINITIAL>	"]"	{return new Symbol(sym.RSQPAREN);}

<YYINITIAL>	"proc"	{return new Symbol(sym.PROC);}
<YYINITIAL>	"def"	{return new Symbol(sym.DEF);}


<YYINITIAL> "if" {return new Symbol(sym.IF);}
<YYINITIAL> "then" {return new Symbol(sym.THEN);}
<YYINITIAL> "else" {return new Symbol(sym.ELSE);}

<YYINITIAL>	"pair"	{return new Symbol(sym.PAIR);}
<YYINITIAL>	"car"	{return new Symbol(sym.CAR);}
<YYINITIAL>	"cdr"	{return new Symbol(sym.CDR);}
<YYINITIAL>	"pair?"	{return new Symbol(sym.PAIR_PRED);}
<YYINITIAL>	"list"	{return new Symbol(sym.LIST);}

<YYINITIAL>	"[:"	{return new Symbol(sym.VECT_OPEN);}
<YYINITIAL>	":]"	{return new Symbol(sym.VECT_CLOSE);}
<YYINITIAL>	"size"	{return new Symbol(sym.SIZE);}

<YYINITIAL>	"eqv?"	{return new Symbol(sym.EQUIV_PRED);}
<YYINITIAL>	"equal?"	{return new Symbol(sym.EQUAL_PRED);}

<YYINITIAL>	"substr"	{return new Symbol(sym.SUBSTR);}


<YYINITIAL>	"#t" {return new Symbol(sym.TRUE);}
<YYINITIAL>	"#f" {return new Symbol(sym.FALSE);}


<YYINITIAL>	"#c"{alpha} {return new Symbol(sym.CHAR,
			new Character(yytext().charAt(2)));}
<YYINITIAL>	"#u"{hex}{4} {
			int code = Integer.parseInt(yytext().substring(2), 16);
			char[] c = Character.toChars(code);
			return new Symbol(sym.CHAR, new Character(c[0]));
		}



<YYINITIAL>	"\""{allChar}*"\"" 	{return new Symbol(sym.STRING, yytext());}

<YYINITIAL>    {alpha}{alphanum}* {
	       // VAR
	       return new Symbol(sym.VAR, yytext());
		}


<YYINITIAL>    [-]{0,1}[0-9]+ {return new Symbol(sym.INT, new Integer(yytext()));}

<YYINITIAL>    [-]{0,1}"#x"{hex}+ {
			String I = yytext().replaceFirst("#x", "");
			int i = Integer.parseInt(I, 16);
			return new Symbol(sym.INT, new Integer(i));
		}
<YYINITIAL>    [-]{0,1}"#b"[01]+ {
			String I = yytext().replaceFirst("#b", "");
			int i = Integer.parseInt(I, 2);
			return new Symbol(sym.INT, new Integer(i));
		}


<YYINITIAL>    [-]{0,1}[([0-9]+\.[0-9]+) | (\.[0-9]+) | ([0-9]+\.)] {
			// DOUBLE
	    	return new Symbol(sym.DOUBLE, new Double(yytext()));
		}




<YYINITIAL>    \S		{ // error situation
	       String msg = String.format("Unrecognised Token: %s", yytext());
	       throw new TokenException(msg);
	    }