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
%xstates BLK_COMMENT

nl = [\n\r]

cc = ([\b\f]|{nl})

ws = {cc}|[\t ]

space = [ \r\t]

alpha = [a-zA-Z_]

alphanum = {alpha}|[0-9]

special = ["+""-""*"/%"^"&|~@"?"!#]

varChar = {alphanum}|{special}

varStartChar = {alphanum}| !(!{special}|![^"/*""//"#])

stringChar = [^\"\\\r\n\t]

stringBreak = {nl}+({nl}|{ws})*|[\t]+ 

blockCommentChar = [^"/*""*/"]

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

<YYINITIAL>	{space}"<"{space}	{return new Symbol(sym.LESS);}
<YYINITIAL>	{space}"<="{space}	{return new Symbol(sym.LESSEQ);}
<YYINITIAL>	{space}"="{space}	{return new Symbol(sym.EQUAL);}
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


<YYINITIAL>	"//"~{nl}	{/* Line comment. Do Nothing*/}

<YYINITIAL>	"/*"	{nestedBlockCommentCount += 1;
					yychar -= 2;
					yybegin(BLK_COMMENT);}
<BLK_COMMENT> {
	"/*"	{nestedBlockCommentCount += 1;
			yychar -= 2;}
	"*/"	{nestedBlockCommentCount -= 1;
			yychar -= 2;
			if (nestedBlockCommentCount == 0){
				yybegin(YYINITIAL);
			}}
	{blockCommentChar}+	{yychar -= yytext().length();}
}


<YYINITIAL>    {varStartChar}*{alpha}{varChar}* {
	       // VAR
	       return new Symbol(sym.VAR, yytext());
		}


<YYINITIAL>    "- "{0,1}[0-9]+ {
				String num = yytext().replaceAll(" ", "");
				return new Symbol(sym.INT, new Double(num));}
<YYINITIAL>    "- "{0,1}"#x"{hex}+ {
			String num = yytext().replaceFirst("#x", "");
			num = num.replaceAll(" ", "");
			int i = Integer.parseInt(num, 16);
			return new Symbol(sym.INT, new Double(i));
		}
<YYINITIAL>    "- "{0,1}"#b"[01]+ {
			String num = yytext().replaceFirst("#b", "");
			num = yytext().replaceAll(" ", "");
			int i = Integer.parseInt(num, 2);
			return new Symbol(sym.INT, new Double(i));
		}

<YYINITIAL>    "- "{0,1}(([0-9]+\.[0-9]+) | (\.[0-9]+) | ([0-9]+\.)) {
			// DOUBLE
			String num = yytext().replaceAll(" ", "");
	    	return new Symbol(sym.DOUBLE, new Double(num));
		}

<YYINITIAL>	"#t"	{return new Symbol(sym.BOOL, new Boolean(true));}
<YYINITIAL>	"#f"	{return new Symbol(sym.BOOL, new Boolean(false));}

<YYINITIAL>	"#c"{alpha}	{return new Symbol(sym.CHAR,
						new Character(yytext().charAt(2)));}
<YYINITIAL>	"#u"{hex}{4}	{int code = Integer.parseInt(yytext().substring(2), 16);
							char[] c = Character.toChars(code);
							return new Symbol(sym.CHAR, new Character(c[0]));}

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

	{stringBreak}	{ /* User generated line break within a string. Do nothing */ }
	{stringChar}+	{strBuff.append(yytext());}
}

<YYINITIAL>	"#e"	{return new Symbol(sym.NIL);}

<YYINITIAL>    \S		{ // error situation
	       String msg = String.format("Unrecognised Token: %s", yytext());
	       throw new TokenException(msg);
	    }
