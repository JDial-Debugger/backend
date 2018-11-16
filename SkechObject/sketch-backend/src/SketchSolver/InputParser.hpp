/* A Bison parser, made by GNU Bison 2.5.  */

/* Bison interface for Yacc-like parsers in C
   
      Copyright (C) 1984, 1989-1990, 2000-2011 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     T_dbl = 258,
     T_int = 259,
     T_bool = 260,
     T_ident = 261,
     T_OutIdent = 262,
     T_NativeCode = 263,
     T_string = 264,
     T_true = 265,
     T_false = 266,
     T_vartype = 267,
     T_rightAC = 268,
     T_leftAC = 269,
     T_rightTC = 270,
     T_leftTC = 271,
     T_arrow = 272,
     T_twoS = 273,
     T_ppls = 274,
     T_mmns = 275,
     T_eq = 276,
     T_neq = 277,
     T_and = 278,
     T_or = 279,
     T_For = 280,
     T_ge = 281,
     T_le = 282,
     T_Native = 283,
     T_NativeMethod = 284,
     T_Sketches = 285,
     T_new = 286,
     T_Typedef = 287,
     T_def = 288,
     T_mdldef = 289,
     T_Min = 290,
     T_assert = 291,
     T_assume = 292,
     T_eof = 293
   };
#endif
/* Tokens.  */
#define T_dbl 258
#define T_int 259
#define T_bool 260
#define T_ident 261
#define T_OutIdent 262
#define T_NativeCode 263
#define T_string 264
#define T_true 265
#define T_false 266
#define T_vartype 267
#define T_rightAC 268
#define T_leftAC 269
#define T_rightTC 270
#define T_leftTC 271
#define T_arrow 272
#define T_twoS 273
#define T_ppls 274
#define T_mmns 275
#define T_eq 276
#define T_neq 277
#define T_and 278
#define T_or 279
#define T_For 280
#define T_ge 281
#define T_le 282
#define T_Native 283
#define T_NativeMethod 284
#define T_Sketches 285
#define T_new 286
#define T_Typedef 287
#define T_def 288
#define T_mdldef 289
#define T_Min 290
#define T_assert 291
#define T_assume 292
#define T_eof 293




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
{

/* Line 2068 of yacc.c  */
#line 29 "InputParser/InputParser.yy"

	int intConst;
	bool boolConst;
	std::string* strConst;
	double doubleConst;		
	std::list<int>* iList;
	list<bool_node*>* nList;
	list<string*>* sList;
	vartype variableType;
	BooleanDAG* bdag;
	bool_node* bnode;
    OutType* otype;
    vector<OutType*>* tVector;



/* Line 2068 of yacc.c  */
#line 143 "InputParser.hpp"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif




