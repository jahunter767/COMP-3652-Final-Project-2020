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
	StringBuffer strBuff = new StringBuffer();

	int nestedBlockCommentCount = 0;

    public int getChar() {
	return yychar + 1;
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

%xstates SMPL_STRING, CHAR_ESCAPE
%xstates LINE_COMMENT, BLK_COMMENT

nl = [\n\r]

cc = ([\b\f]|{nl})

ws = {cc}|[\t ]

space = [ \r\t]

alpha = [a-zA-Z_]

alphanum = {alpha}|[0-9]

special = [\+-\*/%\^&|~@\?!]

allChar = {alphanum}|{special}

stringChars = ~[\"\\]

hex = [0-9A-Fa-f]

%%

<YYINITIAL>	{nl}	{//skip newline, but reset char counter
			 yychar = 0;
			}
<YYINITIAL>	{ws}	{
			 // skip whitespace
			}

<YYINITIAL>	{space}"and"{space}	{return new Symbol(sym.AND);}
<YYINITIAL>	{space}"or"{space}	{return new Symbol(sym.OR);}
<YYINITIAL>	{space}"not"{space}	{return new Symbol(sym.NOT);}

<YYINITIAL>	{space}"<"{space}	{return new Symbol(sym.LESS);}
<YYINITIAL>	{space}"<="{space}	{return new Symbol(sym.LESSEQ);}
<YYINITIAL>	{space}"=="{space}	{return new Symbol(sym.EQUAL);}
<YYINITIAL>	{space}">="{space}	{return new Symbol(sym.GREATEREQ);}
<YYINITIAL>	{space}">"{space}	{return new Symbol(sym.GREATER);}
<YYINITIAL>	{space}"!="{space}	{return new Symbol(sym.NEQUAL);}

<YYINITIAL>	{space}"+"{space}	{return new Symbol(sym.PLUS);}
<YYINITIAL>	{space}"-"{space}	{return new Symbol(sym.MINUS);}
<YYINITIAL>	{space}"*"{space}	{return new Symbol(sym.MUL);}
<YYINITIAL>	{space}"/"{space}	{return new Symbol(sym.DIV);}
<YYINITIAL>	{space}"%"{space}	{return new Symbol(sym.MOD);}
<YYINITIAL>	{space}"^"{space}	{return new Symbol(sym.EXP);}

<YYINITIAL>	{space}"&"{space}	{return new Symbol(sym.BIT_AND);}
<YYINITIAL>	{space}"|"{space}	{return new Symbol(sym.BIT_OR);}
<YYINITIAL>	{space}"~"{space}	{return new Symbol(sym.BIT_NOT);}

<YYINITIAL>	{space}"@"{space}	{return new Symbol(sym.LSTCONCAT);}

<YYINITIAL>	{space}":="{space}	{return new Symbol(sym.ASSIGN);}
<YYINITIAL>	";"	{return new Symbol(sym.SEMI);}
<YYINITIAL>	"{"	{return new Symbol(sym.LBRACE);}
<YYINITIAL>	"}"	{return new Symbol(sym.RBRACE);}
<YYINITIAL>	"("	{return new Symbol(sym.LPAREN);}
<YYINITIAL>	")"	{return new Symbol(sym.RPAREN);}
<YYINITIAL>	"["	{return new Symbol(sym.LSQPAREN);}
<YYINITIAL>	"]"	{return new Symbol(sym.RSQPAREN);}
<YYINITIAL>	","	{return new Symbol(sym.COMMA);}
<YYINITIAL>	":"	{return new Symbol(sym.COLON);}

<YYINITIAL>	"def"	{return new Symbol(sym.DEF);}

<YYINITIAL>	"proc"	{return new Symbol(sym.PROC);}
<YYINITIAL>	{space}"."{space}	{return new Symbol(sym.DOT);}
<YYINITIAL>	"call"	{return new Symbol(sym.CALL);}

<YYINITIAL>	"let"	{return new Symbol(sym.LET);}
<YYINITIAL>	{space}"="{space}	{return new Symbol(sym.BIND);}

<YYINITIAL> "if" {return new Symbol(sym.IF);}
<YYINITIAL> "then" {return new Symbol(sym.THEN);}
<YYINITIAL> "else" {return new Symbol(sym.ELSE);}
<YYINITIAL>	"case"	{return new Symbol(sym.CASE);}

<YYINITIAL>	"[:"	{return new Symbol(sym.VECT_OPEN);}
<YYINITIAL>	":]"	{return new Symbol(sym.VECT_CLOSE);}

<YYINITIAL>	"print"	{return new Symbol(sym.PRINT);}
<YYINITIAL>	"println"	{return new Symbol(sym.PRINTLN);}

<YYINITIAL>	"read"	{return new Symbol(sym.READ);}
<YYINITIAL>	"readint"	{return new Symbol(sym.READINT);}


<YYINITIAL>	"//"	{yychar -= 2;
					yybegin(LINE_COMMENT);}
<LINE_COMMENT> {
	{nl}	{yychar -=2;
			yybegin(YYINITIAL);}
	{~nl}+	{yychar -= yytext().length();}
}

<YYINITIAL>	"/*"	{nestedBlockCommentCount += 1;
					yybegin(BLK_COMMENT);}
<BLK_COMMENT> {
	"*/"	{nestedBlockCommentCount -= 1;
			yychar -= 2;
			if !(nestedBlockCommentCount){
				yybegin(YYINITIAL);
			}}
	{~"*/"}+	{yychar -= yytext().length();}
}


<YYINITIAL>    {allChar}*{alpha}{allChar}* {
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

<YYINITIAL>	"#t"	{return new Symbol(sym.BOOL, new Boolean(True));}
<YYINITIAL>	"#f"	{return new Symbol(sym.BOOL, new Boolean(False));}

<YYINITIAL>	"#c"{alpha}	{return new Symbol(sym.CHAR,
						new Character(yytext().charAt(2)));}
<YYINITIAL>	"#u"{hex}{4}	{int code = Integer.parseInt(yytext().substring(2), 16);
							char[] c = Character.toChars(code);
							return new Symbol(sym.CHAR, new Character(c[0]);}

<YYINITIAL>	\"	{yybegin(SMPL_STRING);}
<SMPL_STRING> {
	\"	{yybegin(YYINITIAL);
        Symbol s = new Symbol(sym.STRING, strBuff.toString());
		strBuff = new StringBuffer();
		return s;}

	\\	{yybegin(CHAR_ESCAPE);}

	<CHAR_ESCAPE> {
		\\	{yybegin(SMPL_STRING);
            strBuff.append("\\");}
		n	{yybegin(SMPL_STRING);
            strBuff.append("\n");}
		t	{yybegin(SMPL_STRING);
            strBuff.append("\t");}
	}

	{stringChars}+	{strBuff.append(yytext());}
}

<YYINITIAL>	"#e"	{return new Symbol(sym.NIL);}

<YYINITIAL>    \S		{ // error situation
	       String msg = String.format("Unrecognised Token: %s", yytext());
	       throw new TokenException(msg);
	    }
