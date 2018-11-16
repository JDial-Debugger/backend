/* A Bison parser, made by GNU Bison 2.5.  */

/* Bison implementation for Yacc-like parsers in C
   
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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.5"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 1

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1

/* Using locations.  */
#define YYLSP_NEEDED 0



/* Copy the first part of user declarations.  */

/* Line 268 of yacc.c  */
#line 1 "InputParser/InputParser.yy"


using namespace std;

BooleanDAGCreator* currentBD;
stack<string> namestack;
vartype Gvartype;
string tupleName;

bool isModel;




#ifdef CONST
#undef CONST
#endif


#define YYLEX_PARAM yyscanner
#define YYPARSE_PARAM yyscanner
#define YY_DECL int yylex (YYSTYPE* yylval, yyscan_t yyscanner)
extern int yylex (YYSTYPE* yylval, yyscan_t yyscanner);



/* Line 268 of yacc.c  */
#line 98 "InputParser.cpp"

/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 1
#endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Enabling the token table.  */
#ifndef YYTOKEN_TABLE
# define YYTOKEN_TABLE 0
#endif


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

/* Line 293 of yacc.c  */
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



/* Line 293 of yacc.c  */
#line 227 "InputParser.cpp"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif


/* Copy the second part of user declarations.  */


/* Line 343 of yacc.c  */
#line 239 "InputParser.cpp"

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#elif (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
typedef signed char yytype_int8;
#else
typedef short int yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(msgid) dgettext ("bison-runtime", msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(msgid) msgid
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(e) ((void) (e))
#else
# define YYUSE(e) /* empty */
#endif

/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(n) (n)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int yyi)
#else
static int
YYID (yyi)
    int yyi;
#endif
{
  return yyi;
}
#endif

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (YYID (0))
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
	     && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
	 || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)				\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack_alloc, Stack, yysize);			\
	Stack = &yyptr->Stack_alloc;					\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (YYID (0))

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from FROM to TO.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(To, From, Count) \
      __builtin_memcpy (To, From, (Count) * sizeof (*(From)))
#  else
#   define YYCOPY(To, From, Count)		\
      do					\
	{					\
	  YYSIZE_T yyi;				\
	  for (yyi = 0; yyi < (Count); yyi++)	\
	    (To)[yyi] = (From)[yyi];		\
	}					\
      while (YYID (0))
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  5
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   439

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  63
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  34
/* YYNRULES -- Number of rules.  */
#define YYNRULES  117
/* YYNRULES -- Number of states.  */
#define YYNSTATES  296

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   293

#define YYTRANSLATE(YYX)						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    47,     2,     2,    57,    42,    58,     2,
      51,    52,    40,    39,    50,    62,    61,    41,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    46,    55,
      43,    56,    44,    45,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    48,     2,    49,    60,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    53,    59,    54,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const yytype_uint16 yyprhs[] =
{
       0,     0,     3,     7,     8,    11,    14,    16,    17,    21,
      23,    26,    29,    32,    36,    40,    47,    54,    62,    70,
      71,    78,    79,    86,    93,   100,   101,   103,   107,   109,
     111,   112,   122,   124,   126,   127,   130,   135,   136,   139,
     140,   145,   149,   150,   155,   161,   162,   165,   168,   169,
     172,   174,   179,   191,   196,   200,   206,   211,   214,   215,
     217,   221,   225,   229,   233,   237,   241,   245,   250,   256,
     261,   270,   279,   286,   290,   297,   301,   305,   309,   313,
     317,   321,   325,   329,   333,   337,   343,   344,   347,   349,
     352,   354,   356,   373,   389,   392,   395,   399,   401,   405,
     410,   416,   421,   427,   434,   436,   440,   444,   446,   450,
     454,   458,   462,   464,   467,   469,   471,   473
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const yytype_int8 yyrhs[] =
{
      64,     0,    -1,    80,    65,    38,    -1,    -1,    74,    65,
      -1,    82,    65,    -1,     6,    -1,    -1,     6,    67,    66,
      -1,     6,    -1,     6,    68,    -1,    12,     6,    -1,     6,
       6,    -1,    47,    12,     6,    -1,    47,     6,     6,    -1,
      12,    48,    40,    92,    49,     6,    -1,     6,    48,    40,
      92,    49,     6,    -1,    47,    12,    48,    40,    92,    49,
       6,    -1,    47,     6,    48,    40,    92,    49,     6,    -1,
      -1,    12,    48,    92,    49,    70,    66,    -1,    -1,     6,
      48,    92,    49,    71,    66,    -1,    47,    12,    48,    92,
      49,    68,    -1,    47,     6,    48,    92,    49,    68,    -1,
      -1,    69,    -1,    69,    50,    72,    -1,    34,    -1,    33,
      -1,    -1,    73,     6,    75,    51,    72,    52,    53,    85,
      54,    -1,    12,    -1,     6,    -1,    -1,    77,    76,    -1,
       6,    51,    77,    52,    -1,    -1,    79,    78,    -1,    -1,
      32,    53,    79,    54,    -1,     6,    30,     6,    -1,    -1,
      36,    83,    81,    55,    -1,     6,    51,    84,    52,    55,
      -1,    -1,     6,    84,    -1,     9,    84,    -1,    -1,    85,
      86,    -1,    55,    -1,     6,    56,    88,    55,    -1,    57,
      90,    18,    89,    57,    48,    88,    49,    56,    88,    55,
      -1,     7,    56,    88,    55,    -1,    36,    88,    55,    -1,
      36,    88,    46,     9,    55,    -1,    37,    88,    87,    55,
      -1,    46,     9,    -1,    -1,    91,    -1,    91,    58,    91,
      -1,    91,    23,    91,    -1,    91,    59,    91,    -1,    91,
      24,    91,    -1,    91,    60,    91,    -1,    91,    22,    91,
      -1,    91,    21,    91,    -1,     6,    48,    88,    49,    -1,
      91,    61,    48,    94,    49,    -1,    94,    48,    88,    49,
      -1,     6,    48,    48,    88,    17,    88,    49,    49,    -1,
      94,    48,    48,    88,    17,    88,    49,    49,    -1,    57,
      89,    57,    48,    88,    49,    -1,    14,    89,    13,    -1,
      48,     6,    49,    16,    89,    15,    -1,    18,    89,    18,
      -1,    91,    39,    91,    -1,    91,    41,    91,    -1,    91,
      42,    91,    -1,    91,    40,    91,    -1,    91,    62,    91,
      -1,    91,    44,    91,    -1,    91,    43,    91,    -1,    91,
      26,    91,    -1,    91,    27,    91,    -1,    88,    45,    88,
      46,    88,    -1,    -1,    91,    89,    -1,     6,    -1,     6,
      90,    -1,    95,    -1,     3,    -1,     6,    48,    40,     6,
      49,    51,    89,    52,    51,    88,    52,    48,     6,    50,
      95,    49,    -1,     6,    48,    12,    49,    51,    89,    52,
      51,    88,    52,    48,     6,    50,    95,    49,    -1,    62,
      91,    -1,    47,    91,    -1,    51,    88,    52,    -1,    96,
      -1,    43,    96,    44,    -1,    43,    96,    95,    44,    -1,
      43,    96,    95,    40,    44,    -1,    35,    43,    96,    44,
      -1,    35,    43,    96,    95,    44,    -1,    35,    43,    96,
      95,    40,    44,    -1,    93,    -1,    92,    39,    93,    -1,
      92,    62,    93,    -1,    94,    -1,    51,    93,    52,    -1,
      93,    40,    93,    -1,    93,    41,    93,    -1,    93,    42,
      93,    -1,    95,    -1,    62,    95,    -1,     4,    -1,    10,
      -1,    11,    -1,     6,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,   117,   117,   120,   121,   122,   125,   141,   141,   157,
     158,   164,   177,   184,   194,   200,   214,   218,   227,   230,
     230,   231,   231,   232,   233,   236,   237,   238,   240,   240,
     243,   242,   259,   266,   270,   271,   276,   282,   283,   285,
     286,   289,   294,   294,   304,   314,   317,   321,   327,   328,
     331,   332,   336,   364,   369,   376,   387,   404,   405,   407,
     408,   411,   414,   417,   420,   423,   427,   430,   434,   442,
     445,   452,   458,   472,   486,   501,   514,   518,   522,   526,
     529,   533,   537,   540,   544,   548,   559,   560,   571,   575,
     580,   583,   587,   632,   687,   690,   694,   697,   702,   706,
     714,   719,   723,   731,   738,   739,   740,   742,   743,   744,
     745,   747,   751,   752,   755,   756,   757,   759
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || YYTOKEN_TABLE
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "T_dbl", "T_int", "T_bool", "T_ident",
  "T_OutIdent", "T_NativeCode", "T_string", "T_true", "T_false",
  "T_vartype", "T_rightAC", "T_leftAC", "T_rightTC", "T_leftTC", "T_arrow",
  "T_twoS", "T_ppls", "T_mmns", "T_eq", "T_neq", "T_and", "T_or", "T_For",
  "T_ge", "T_le", "T_Native", "T_NativeMethod", "T_Sketches", "T_new",
  "T_Typedef", "T_def", "T_mdldef", "T_Min", "T_assert", "T_assume",
  "T_eof", "'+'", "'*'", "'/'", "'%'", "'<'", "'>'", "'?'", "':'", "'!'",
  "'['", "']'", "','", "'('", "')'", "'{'", "'}'", "';'", "'='", "'$'",
  "'&'", "'|'", "'^'", "'.'", "'-'", "$accept", "Program", "MethodList",
  "InList", "$@1", "OutList", "ParamDecl", "$@2", "$@3", "ParamList",
  "Mhelp", "Method", "$@4", "TupleType", "TupleTypeList", "TypeLine",
  "TypeList", "Typedef", "AssertionExpr", "HLAssertion", "$@5",
  "TokenList", "WorkBody", "WorkStatement", "OptionalMsg", "Expression",
  "varList", "IdentList", "Term", "ConstantExpr", "ConstantTerm",
  "NegConstant", "Constant", "Ident", 0
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,    43,
      42,    47,    37,    60,    62,    63,    58,    33,    91,    93,
      44,    40,    41,   123,   125,    59,    61,    36,    38,   124,
      94,    46,    45
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    63,    64,    65,    65,    65,    66,    67,    66,    68,
      68,    69,    69,    69,    69,    69,    69,    69,    69,    70,
      69,    71,    69,    69,    69,    72,    72,    72,    73,    73,
      75,    74,    76,    76,    77,    77,    78,    79,    79,    80,
      80,    81,    83,    82,    82,    84,    84,    84,    85,    85,
      86,    86,    86,    86,    86,    86,    86,    87,    87,    88,
      88,    88,    88,    88,    88,    88,    88,    88,    88,    88,
      88,    88,    88,    88,    88,    88,    88,    88,    88,    88,
      88,    88,    88,    88,    88,    88,    89,    89,    90,    90,
      91,    91,    91,    91,    91,    91,    91,    91,    91,    91,
      91,    91,    91,    91,    92,    92,    92,    93,    93,    93,
      93,    93,    94,    94,    95,    95,    95,    96
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     3,     0,     2,     2,     1,     0,     3,     1,
       2,     2,     2,     3,     3,     6,     6,     7,     7,     0,
       6,     0,     6,     6,     6,     0,     1,     3,     1,     1,
       0,     9,     1,     1,     0,     2,     4,     0,     2,     0,
       4,     3,     0,     4,     5,     0,     2,     2,     0,     2,
       1,     4,    11,     4,     3,     5,     4,     2,     0,     1,
       3,     3,     3,     3,     3,     3,     3,     4,     5,     4,
       8,     8,     6,     3,     6,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     5,     0,     2,     1,     2,
       1,     1,    16,    15,     2,     2,     3,     1,     3,     4,
       5,     4,     5,     6,     1,     3,     3,     1,     3,     3,
       3,     3,     1,     2,     1,     1,     1,     1
};

/* YYDEFACT[STATE-NAME] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
      39,     0,     0,     3,    37,     1,     0,    29,    28,    42,
       0,     0,     3,     3,     0,    45,     0,     2,    30,     4,
       5,     0,    40,    38,    45,    45,     0,     0,     0,     0,
      34,    46,    47,     0,     0,    43,    25,     0,    44,    41,
       0,     0,     0,    26,     0,    33,    32,    36,    35,    12,
       0,    11,     0,     0,     0,    25,     0,   114,   115,   116,
       0,     0,     0,     0,   104,   107,   112,     0,     0,    14,
       0,    13,     0,    27,    48,     0,     0,   113,     0,    21,
       0,     0,     0,     0,     0,    19,     0,     0,     0,     0,
       0,     0,   108,   105,     0,   106,   109,   110,   111,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    31,
      50,     0,    49,    16,     6,    22,    15,    20,     0,     9,
      24,     0,    23,     0,     0,    91,   117,    86,    86,     0,
       0,     0,     0,     0,    86,     0,     0,    59,     0,    90,
      97,    58,    88,     0,     0,    18,    10,    17,     0,     0,
       0,   117,     0,     0,    86,    90,     0,     0,   117,     0,
      95,     0,     0,     0,    94,    90,     0,     0,    54,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    89,
      86,     8,    51,    53,     0,     0,     0,     0,     0,    73,
      87,    75,     0,    98,     0,     0,    96,     0,     0,     0,
      66,    65,    61,    63,    83,    84,    76,    79,    77,    78,
      82,    81,    60,    62,    64,     0,    80,     0,     0,    57,
      56,     0,     0,     0,   117,     0,    67,   101,     0,     0,
      99,    86,     0,     0,    55,     0,     0,    69,     0,    86,
       0,     0,     0,   102,   100,     0,     0,    85,    68,     0,
       0,     0,    86,     0,   103,    74,    72,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    70,    71,     0,     0,
       0,     0,     0,     0,    52,     0,     0,     0,     0,     0,
       0,     0,     0,    93,     0,    92
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     2,    10,   115,   144,   120,    43,   100,    94,    44,
      11,    12,    29,    48,    37,    23,    14,     3,    28,    13,
      16,    26,    90,   112,   188,   136,   153,   143,   137,    63,
      64,   138,   155,   140
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -128
static const yytype_int16 yypact[] =
{
     -23,   -32,    46,   123,  -128,  -128,    11,  -128,  -128,  -128,
      40,    89,   123,   123,    -1,   159,    98,  -128,  -128,  -128,
    -128,    38,  -128,  -128,   159,   159,    74,   143,    73,    86,
    -128,  -128,  -128,   131,   190,  -128,    13,    12,  -128,  -128,
      20,    31,    17,   148,   153,  -128,  -128,  -128,  -128,  -128,
      87,  -128,   120,    34,    48,    13,   174,  -128,  -128,  -128,
       4,     4,   230,   140,   209,  -128,  -128,     4,   158,  -128,
     302,  -128,   316,  -128,  -128,   164,   317,  -128,     4,  -128,
       4,     4,     4,     4,   183,  -128,     4,   184,     4,   233,
     126,   223,  -128,   209,   246,   209,   275,   226,  -128,   269,
     246,   237,   274,   245,   274,   231,   247,   267,   267,  -128,
    -128,   283,  -128,  -128,   303,  -128,  -128,  -128,   305,   274,
    -128,   313,  -128,   267,   267,  -128,   273,    41,    41,   280,
     322,    41,   333,   267,    41,    41,   121,   369,   297,   304,
    -128,   286,   283,   332,   246,  -128,  -128,  -128,   -42,    57,
     107,   320,    41,   357,    41,  -128,   359,   322,  -128,    95,
    -128,   330,   -10,   324,  -128,   334,   267,   374,  -128,    41,
      41,    41,    41,    41,    41,    41,    41,    41,    41,    41,
      41,    41,    41,    41,   338,    41,   287,   378,   342,  -128,
      41,  -128,  -128,  -128,   340,   392,   337,   146,    -6,  -128,
    -128,  -128,   130,  -128,   181,   384,  -128,   353,   291,   347,
    -128,  -128,  -128,  -128,  -128,  -128,  -128,  -128,  -128,  -128,
    -128,  -128,  -128,  -128,  -128,    83,  -128,   337,   198,  -128,
    -128,   346,   354,   355,   312,   103,  -128,  -128,   204,   362,
    -128,    41,   267,   267,  -128,   358,   127,  -128,   366,    41,
     364,   267,   372,  -128,  -128,   402,   234,  -128,  -128,   267,
     267,   367,    41,   243,  -128,  -128,  -128,   251,   259,   370,
     368,   373,   375,   376,   267,   382,  -128,  -128,   267,    63,
     267,   106,   377,   142,  -128,   412,   386,   385,   417,   230,
     387,   389,   230,  -128,   390,  -128
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
    -128,  -128,   350,   -96,  -128,   -18,  -128,  -128,  -128,   371,
    -128,  -128,  -128,  -128,  -128,  -128,  -128,  -128,  -128,  -128,
    -128,   341,  -128,  -128,  -128,   -43,  -127,   294,    84,   118,
     293,   -11,   -50,  -114
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -114
static const yytype_int16 yytable[] =
{
      66,   156,    66,   166,   117,    21,   194,   163,    57,     1,
      66,    66,    77,   192,    58,    59,   159,    66,    45,    40,
      66,     4,    66,    53,    46,    41,    49,   200,    66,    54,
      66,    66,    66,    66,   195,   166,    66,    51,    66,    65,
      69,    65,   206,   202,   125,    57,     5,   151,   191,    65,
      65,    58,    59,    22,    71,    61,    65,   139,   139,    65,
      42,    65,    15,   231,    47,   141,    62,    65,    50,    65,
      65,    65,    65,   139,   139,    65,   129,    65,    17,    52,
     148,   149,    70,   139,   130,   165,   122,    57,   131,    30,
     162,    57,   133,    58,    59,    18,    72,    58,    59,    57,
     139,   146,   166,   152,    27,    58,    59,   197,   166,   204,
     125,    57,   193,   126,   255,   282,   139,    58,    59,   194,
     251,   127,   261,   208,    57,   128,    33,    60,    35,     6,
      58,    59,   105,   106,    57,   270,   139,    36,    61,   203,
      58,    59,   129,   228,   259,    62,   139,   195,   166,    62,
     130,   166,   238,   235,   131,   196,     7,     8,   133,     9,
      67,   284,   107,   108,   134,    24,   166,   167,    25,   135,
      68,    61,   166,    34,   237,    66,   168,   139,    75,    78,
     109,   110,    62,   111,   246,    84,    38,   166,    87,    79,
      89,   166,   139,   139,   286,   236,    39,    78,    55,   256,
     257,   139,    80,    78,   101,    56,   103,    85,   263,   139,
     139,   154,   154,    91,   245,   160,   267,   268,   154,   164,
      80,   239,    78,    78,   139,   240,    80,    74,   139,   113,
     139,   279,    99,   102,    57,   281,   164,   283,   154,   291,
      58,    59,   294,   166,   252,    80,    80,   247,   253,    81,
      82,    83,   114,   210,   211,   212,   213,   214,   215,   216,
     217,   218,   219,   220,   221,   222,   223,   224,    83,   226,
     125,    57,    78,   126,   154,   116,    78,    58,    59,   166,
     119,   127,   104,   266,    78,   128,   118,   123,   166,   142,
     125,    57,   271,   126,   121,    80,   166,    58,    59,    80,
     272,   127,   129,   124,   166,   128,    57,    80,   273,    -7,
     130,   145,    58,    59,   131,   132,    82,    83,   133,   147,
      57,   150,   129,   157,   134,   154,    58,    59,   158,   135,
     130,   166,   187,   154,   131,   227,   166,   243,   133,   161,
     125,    57,    86,   234,   134,   186,   154,    58,    59,   135,
     190,   127,  -112,    61,    76,   128,    88,    81,    82,    83,
     150,   205,    19,    20,    62,    31,    32,    61,   198,    92,
     199,    93,   129,    95,    96,    97,    98,   201,    62,   205,
     130,   207,  -113,   209,   131,   132,   225,   229,   133,   232,
     169,   170,   171,   172,   134,   173,   174,   230,   233,   135,
     241,   242,   244,   248,   250,   249,   254,   258,   175,   176,
     177,   178,   179,   180,   260,   262,   264,   265,   287,   269,
     275,   274,   276,   290,   277,   285,    73,   181,   182,   183,
     184,   185,   278,   280,   288,   289,   189,   292,   293,   295
};

#define yypact_value_is_default(yystate) \
  ((yystate) == (-128))

#define yytable_value_is_error(yytable_value) \
  YYID (0)

static const yytype_uint16 yycheck[] =
{
      50,   128,    52,    45,   100,     6,    12,   134,     4,    32,
      60,    61,    62,    55,    10,    11,   130,    67,     6,     6,
      70,    53,    72,     6,    12,    12,     6,   154,    78,    12,
      80,    81,    82,    83,    40,    45,    86,     6,    88,    50,
       6,    52,    52,   157,     3,     4,     0,     6,   144,    60,
      61,    10,    11,    54,     6,    51,    67,   107,   108,    70,
      47,    72,    51,   190,    52,   108,    62,    78,    48,    80,
      81,    82,    83,   123,   124,    86,    35,    88,    38,    48,
     123,   124,    48,   133,    43,   135,   104,     4,    47,    51,
     133,     4,    51,    10,    11,     6,    48,    10,    11,     4,
     150,   119,    45,    62,     6,    10,    11,   150,    45,   159,
       3,     4,    55,     6,   241,    52,   166,    10,    11,    12,
      17,    14,   249,   166,     4,    18,    52,    40,    55,     6,
      10,    11,     6,     7,     4,   262,   186,    51,    51,    44,
      10,    11,    35,   186,    17,    62,   196,    40,    45,    62,
      43,    45,   202,   196,    47,    48,    33,    34,    51,    36,
      40,    55,    36,    37,    57,     6,    45,    46,     9,    62,
      52,    51,    45,    30,    44,   225,    55,   227,    60,    39,
      54,    55,    62,    57,   227,    67,    55,    45,    70,    49,
      72,    45,   242,   243,    52,    49,     6,    39,    50,   242,
     243,   251,    62,    39,    86,    52,    88,    49,   251,   259,
     260,   127,   128,    49,   225,   131,   259,   260,   134,   135,
      62,    40,    39,    39,   274,    44,    62,    53,   278,     6,
     280,   274,    49,    49,     4,   278,   152,   280,   154,   289,
      10,    11,   292,    45,    40,    62,    62,    49,    44,    40,
      41,    42,     6,   169,   170,   171,   172,   173,   174,   175,
     176,   177,   178,   179,   180,   181,   182,   183,    42,   185,
       3,     4,    39,     6,   190,     6,    39,    10,    11,    45,
       6,    14,    49,    49,    39,    18,    49,    56,    45,     6,
       3,     4,    49,     6,    49,    62,    45,    10,    11,    62,
      49,    14,    35,    56,    45,    18,     4,    62,    49,     6,
      43,     6,    10,    11,    47,    48,    41,    42,    51,     6,
       4,    48,    35,    43,    57,   241,    10,    11,     6,    62,
      43,    45,    46,   249,    47,    48,    45,    46,    51,     6,
       3,     4,    40,     6,    57,    48,   262,    10,    11,    62,
      18,    14,    48,    51,    61,    18,    40,    40,    41,    42,
      48,    49,    12,    13,    62,    24,    25,    51,    48,    52,
      13,    78,    35,    80,    81,    82,    83,    18,    62,    49,
      43,    57,    48,     9,    47,    48,    48,     9,    51,    49,
      21,    22,    23,    24,    57,    26,    27,    55,     6,    62,
      16,    48,    55,    57,    49,    51,    44,    49,    39,    40,
      41,    42,    43,    44,    48,    51,    44,    15,     6,    52,
      52,    51,    49,     6,    49,    48,    55,    58,    59,    60,
      61,    62,    56,    51,    48,    50,   142,    50,    49,    49
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,    32,    64,    80,    53,     0,     6,    33,    34,    36,
      65,    73,    74,    82,    79,    51,    83,    38,     6,    65,
      65,     6,    54,    78,     6,     9,    84,     6,    81,    75,
      51,    84,    84,    52,    30,    55,    51,    77,    55,     6,
       6,    12,    47,    69,    72,     6,    12,    52,    76,     6,
      48,     6,    48,     6,    12,    50,    52,     4,    10,    11,
      40,    51,    62,    92,    93,    94,    95,    40,    92,     6,
      48,     6,    48,    72,    53,    92,    93,    95,    39,    49,
      62,    40,    41,    42,    92,    49,    40,    92,    40,    92,
      85,    49,    52,    93,    71,    93,    93,    93,    93,    49,
      70,    92,    49,    92,    49,     6,     7,    36,    37,    54,
      55,    57,    86,     6,     6,    66,     6,    66,    49,     6,
      68,    49,    68,    56,    56,     3,     6,    14,    18,    35,
      43,    47,    48,    51,    57,    62,    88,    91,    94,    95,
      96,    88,     6,    90,    67,     6,    68,     6,    88,    88,
      48,     6,    62,    89,    91,    95,    89,    43,     6,    96,
      91,     6,    88,    89,    91,    95,    45,    46,    55,    21,
      22,    23,    24,    26,    27,    39,    40,    41,    42,    43,
      44,    58,    59,    60,    61,    62,    48,    46,    87,    90,
      18,    66,    55,    55,    12,    40,    48,    88,    48,    13,
      89,    18,    96,    44,    95,    49,    52,    57,    88,     9,
      91,    91,    91,    91,    91,    91,    91,    91,    91,    91,
      91,    91,    91,    91,    91,    48,    91,    48,    88,     9,
      55,    89,    49,     6,     6,    88,    49,    44,    95,    40,
      44,    16,    48,    46,    55,    94,    88,    49,    57,    51,
      49,    17,    40,    44,    44,    89,    88,    88,    49,    17,
      48,    89,    51,    88,    44,    15,    49,    88,    88,    52,
      89,    49,    49,    49,    51,    52,    49,    49,    56,    88,
      51,    88,    52,    88,    55,    48,    52,     6,    48,    50,
       6,    95,    50,    49,    95,    49
};

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrorlab


/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  However,
   YYFAIL appears to be in use.  Nevertheless, it is formally deprecated
   in Bison 2.4.2's NEWS entry, where a plan to phase it out is
   discussed.  */

#define YYFAIL		goto yyerrlab
#if defined YYFAIL
  /* This is here to suppress warnings from the GCC cpp's
     -Wunused-macros.  Normally we don't worry about that warning, but
     some users do, and we want to make it easy for users to remove
     YYFAIL uses, which will produce warnings from Bison 2.5.  */
#endif

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)					\
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    {								\
      yychar = (Token);						\
      yylval = (Value);						\
      YYPOPSTACK (1);						\
      goto yybackup;						\
    }								\
  else								\
    {								\
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;							\
    }								\
while (YYID (0))


#define YYTERROR	1
#define YYERRCODE	256


/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#define YYRHSLOC(Rhs, K) ((Rhs)[K])
#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)				\
    do									\
      if (YYID (N))                                                    \
	{								\
	  (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;	\
	  (Current).first_column = YYRHSLOC (Rhs, 1).first_column;	\
	  (Current).last_line    = YYRHSLOC (Rhs, N).last_line;		\
	  (Current).last_column  = YYRHSLOC (Rhs, N).last_column;	\
	}								\
      else								\
	{								\
	  (Current).first_line   = (Current).last_line   =		\
	    YYRHSLOC (Rhs, 0).last_line;				\
	  (Current).first_column = (Current).last_column =		\
	    YYRHSLOC (Rhs, 0).last_column;				\
	}								\
    while (YYID (0))
#endif


/* This macro is provided for backward compatibility. */

#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */

#ifdef YYLEX_PARAM
# define YYLEX yylex (&yylval, YYLEX_PARAM)
#else
# define YYLEX yylex (&yylval)
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (YYID (0))

# define YY_SYMBOL_PRINT(Title, Type, Value, Location)			  \
do {									  \
  if (yydebug)								  \
    {									  \
      YYFPRINTF (stderr, "%s ", Title);					  \
      yy_symbol_print (stderr,						  \
		  Type, Value); \
      YYFPRINTF (stderr, "\n");						  \
    }									  \
} while (YYID (0))


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_value_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# else
  YYUSE (yyoutput);
# endif
  switch (yytype)
    {
      default:
	break;
    }
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (yytype < YYNTOKENS)
    YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
#else
static void
yy_stack_print (yybottom, yytop)
    yytype_int16 *yybottom;
    yytype_int16 *yytop;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (YYID (0))


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_reduce_print (YYSTYPE *yyvsp, int yyrule)
#else
static void
yy_reduce_print (yyvsp, yyrule)
    YYSTYPE *yyvsp;
    int yyrule;
#endif
{
  int yynrhs = yyr2[yyrule];
  int yyi;
  unsigned long int yylno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
	     yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
		       &(yyvsp[(yyi + 1) - (yynrhs)])
		       		       );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (yyvsp, Rule); \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static YYSIZE_T
yystrlen (const char *yystr)
#else
static YYSIZE_T
yystrlen (yystr)
    const char *yystr;
#endif
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static char *
yystpcpy (char *yydest, const char *yysrc)
#else
static char *
yystpcpy (yydest, yysrc)
    char *yydest;
    const char *yysrc;
#endif
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
	switch (*++yyp)
	  {
	  case '\'':
	  case ',':
	    goto do_not_strip_quotes;

	  case '\\':
	    if (*++yyp != '\\')
	      goto do_not_strip_quotes;
	    /* Fall through.  */
	  default:
	    if (yyres)
	      yyres[yyn] = *yyp;
	    yyn++;
	    break;

	  case '"':
	    if (yyres)
	      yyres[yyn] = '\0';
	    return yyn;
	  }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store.  */
static int
yysyntax_error (YYSIZE_T *yymsg_alloc, char **yymsg,
                yytype_int16 *yyssp, int yytoken)
{
  YYSIZE_T yysize0 = yytnamerr (0, yytname[yytoken]);
  YYSIZE_T yysize = yysize0;
  YYSIZE_T yysize1;
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = 0;
  /* Arguments of yyformat. */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Number of reported tokens (one for the "unexpected", one per
     "expected"). */
  int yycount = 0;

  /* There are many possibilities here to consider:
     - Assume YYFAIL is not used.  It's too flawed to consider.  See
       <http://lists.gnu.org/archive/html/bison-patches/2009-12/msg00024.html>
       for details.  YYERROR is fine as it does not invoke this
       function.
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[*yyssp];
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for
             this state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;

          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                yysize1 = yysize + yytnamerr (0, yytname[yyx]);
                if (! (yysize <= yysize1
                       && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
                  return 2;
                yysize = yysize1;
              }
        }
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  yysize1 = yysize + yystrlen (yyformat);
  if (! (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
    return 2;
  yysize = yysize1;

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          yyp++;
          yyformat++;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
#else
static void
yydestruct (yymsg, yytype, yyvaluep)
    const char *yymsg;
    int yytype;
    YYSTYPE *yyvaluep;
#endif
{
  YYUSE (yyvaluep);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  switch (yytype)
    {

      default:
	break;
    }
}


/* Prevent warnings from -Wmissing-prototypes.  */
#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */


/*----------.
| yyparse.  |
`----------*/

#ifdef YYPARSE_PARAM
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void *YYPARSE_PARAM)
#else
int
yyparse (YYPARSE_PARAM)
    void *YYPARSE_PARAM;
#endif
#else /* ! YYPARSE_PARAM */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void)
#else
int
yyparse ()

#endif
#endif
{
/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;

    /* Number of syntax errors so far.  */
    int yynerrs;

    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       `yyss': related to states.
       `yyvs': related to semantic values.

       Refer to the stacks thru separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yytoken = 0;
  yyss = yyssa;
  yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */
  yyssp = yyss;
  yyvsp = yyvs;

  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack.  Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	yytype_int16 *yyss1 = yyss;

	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow (YY_("memory exhausted"),
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),
		    &yystacksize);

	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	yytype_int16 *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyexhaustedlab;
	YYSTACK_RELOCATE (yyss_alloc, yyss);
	YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  *++yyvsp = yylval;

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:

/* Line 1806 of yacc.c  */
#line 117 "InputParser/InputParser.yy"
    {  (yyval.intConst)=0; return 0;}
    break;

  case 3:

/* Line 1806 of yacc.c  */
#line 120 "InputParser/InputParser.yy"
    {}
    break;

  case 4:

/* Line 1806 of yacc.c  */
#line 121 "InputParser/InputParser.yy"
    {}
    break;

  case 5:

/* Line 1806 of yacc.c  */
#line 122 "InputParser/InputParser.yy"
    {}
    break;

  case 6:

/* Line 1806 of yacc.c  */
#line 125 "InputParser/InputParser.yy"
    { 
    if(Gvartype == TUPLE){
        currentBD->create_inputs( -1, OutType::getTuple(tupleName), *(yyvsp[(1) - (1)].strConst));
    }
    else
    if( Gvartype == INT){
		currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT , *(yyvsp[(1) - (1)].strConst)); 
	}else{
		if(Gvartype==FLOAT){
			currentBD->create_inputs(-1, OutType::FLOAT, *(yyvsp[(1) - (1)].strConst)); 
		}else{
			currentBD->create_inputs(-1, OutType::BOOL, *(yyvsp[(1) - (1)].strConst)); 
		}
	}	

}
    break;

  case 7:

/* Line 1806 of yacc.c  */
#line 141 "InputParser/InputParser.yy"
    {
	if(Gvartype == TUPLE){
        currentBD->create_inputs( -1, OutType::getTuple(tupleName), *(yyvsp[(1) - (1)].strConst));
    }
    else
    if( Gvartype == INT){
		currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT , *(yyvsp[(1) - (1)].strConst)); 
	}else{
		if(Gvartype==FLOAT){
			currentBD->create_inputs(-1, OutType::FLOAT, *(yyvsp[(1) - (1)].strConst)); 
		}else{
			currentBD->create_inputs(-1, OutType::BOOL, *(yyvsp[(1) - (1)].strConst)); 
		}
	}	
}
    break;

  case 9:

/* Line 1806 of yacc.c  */
#line 157 "InputParser/InputParser.yy"
    { 	 currentBD->create_outputs(-1, *(yyvsp[(1) - (1)].strConst)); }
    break;

  case 10:

/* Line 1806 of yacc.c  */
#line 158 "InputParser/InputParser.yy"
    {
	
	currentBD->create_outputs(-1, *(yyvsp[(1) - (2)].strConst));
}
    break;

  case 11:

/* Line 1806 of yacc.c  */
#line 164 "InputParser/InputParser.yy"
    {  
	if( (yyvsp[(1) - (2)].variableType) == INT){

		currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT , *(yyvsp[(2) - (2)].strConst)); 
	}else{
		if((yyvsp[(1) - (2)].variableType) == FLOAT){
			currentBD->create_inputs(-1, OutType::FLOAT, *(yyvsp[(2) - (2)].strConst)); 
		}else{
			currentBD->create_inputs(-1, OutType::BOOL, *(yyvsp[(2) - (2)].strConst)); 
		}
	}	
	delete (yyvsp[(2) - (2)].strConst);
}
    break;

  case 12:

/* Line 1806 of yacc.c  */
#line 177 "InputParser/InputParser.yy"
    {

    currentBD->create_inputs( -1 , OutType::getTuple(*(yyvsp[(1) - (2)].strConst)) , *(yyvsp[(2) - (2)].strConst));
       

    delete (yyvsp[(2) - (2)].strConst);
}
    break;

  case 13:

/* Line 1806 of yacc.c  */
#line 184 "InputParser/InputParser.yy"
    {
 	 if( (yyvsp[(2) - (3)].variableType) == INT){

		 currentBD->create_outputs(2 /* NINPUTS */, *(yyvsp[(3) - (3)].strConst));
 	 }else{

	 	 currentBD->create_outputs(-1, *(yyvsp[(3) - (3)].strConst)); 
 	 }
	 delete (yyvsp[(3) - (3)].strConst);
 }
    break;

  case 14:

/* Line 1806 of yacc.c  */
#line 194 "InputParser/InputParser.yy"
    {

    currentBD->create_outputs(-1, *(yyvsp[(3) - (3)].strConst));
    delete (yyvsp[(3) - (3)].strConst);
 }
    break;

  case 15:

/* Line 1806 of yacc.c  */
#line 200 "InputParser/InputParser.yy"
    {  
	if( (yyvsp[(1) - (6)].variableType) == INT){

		currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT_ARR , *(yyvsp[(6) - (6)].strConst), (yyvsp[(4) - (6)].intConst)); 
	}else{
		if((yyvsp[(1) - (6)].variableType) == FLOAT){
			currentBD->create_inputs(-1, OutType::FLOAT_ARR, *(yyvsp[(6) - (6)].strConst), (yyvsp[(4) - (6)].intConst)); 
		}else{
			currentBD->create_inputs(-1, OutType::BOOL_ARR, *(yyvsp[(6) - (6)].strConst), (yyvsp[(4) - (6)].intConst)); 
		}
	}	
	delete (yyvsp[(6) - (6)].strConst);
}
    break;

  case 16:

/* Line 1806 of yacc.c  */
#line 214 "InputParser/InputParser.yy"
    {
    currentBD->create_inputs(-1, OutType::getTuple(*(yyvsp[(1) - (6)].strConst)), *(yyvsp[(6) - (6)].strConst), (yyvsp[(4) - (6)].intConst));
}
    break;

  case 17:

/* Line 1806 of yacc.c  */
#line 218 "InputParser/InputParser.yy"
    {
 	 if( (yyvsp[(2) - (7)].variableType) == INT){
		 currentBD->create_outputs(2 /* NINPUTS */, *(yyvsp[(7) - (7)].strConst));
 	 }else{

	 	 currentBD->create_outputs(-1, *(yyvsp[(7) - (7)].strConst)); 
 	 }
	 delete (yyvsp[(7) - (7)].strConst);
 }
    break;

  case 18:

/* Line 1806 of yacc.c  */
#line 227 "InputParser/InputParser.yy"
    {
  currentBD->create_outputs(-1,*(yyvsp[(7) - (7)].strConst));
 }
    break;

  case 19:

/* Line 1806 of yacc.c  */
#line 230 "InputParser/InputParser.yy"
    {Gvartype = (yyvsp[(1) - (4)].variableType); }
    break;

  case 21:

/* Line 1806 of yacc.c  */
#line 231 "InputParser/InputParser.yy"
    {Gvartype = TUPLE; tupleName = *(yyvsp[(1) - (4)].strConst);}
    break;

  case 28:

/* Line 1806 of yacc.c  */
#line 240 "InputParser/InputParser.yy"
    {isModel=true; }
    break;

  case 29:

/* Line 1806 of yacc.c  */
#line 240 "InputParser/InputParser.yy"
    {isModel=false; }
    break;

  case 30:

/* Line 1806 of yacc.c  */
#line 243 "InputParser/InputParser.yy"
    {		modelBuilding.restart ();
		if(currentBD!= NULL){
			delete currentBD;
		}

		currentBD = envt->newFunction(*(yyvsp[(2) - (2)].strConst), isModel);

		delete (yyvsp[(2) - (2)].strConst);

}
    break;

  case 31:

/* Line 1806 of yacc.c  */
#line 253 "InputParser/InputParser.yy"
    { 
	currentBD->finalize();
	modelBuilding.stop();
}
    break;

  case 32:

/* Line 1806 of yacc.c  */
#line 259 "InputParser/InputParser.yy"
    {
    if( (yyvsp[(1) - (1)].variableType) == INT){ (yyval.otype) = OutType::INT;}
    if( (yyvsp[(1) - (1)].variableType) == BIT){ (yyval.otype) = OutType::BOOL;}
    if( (yyvsp[(1) - (1)].variableType) == INT_ARR){ (yyval.otype) = OutType::INT_ARR;}
    if( (yyvsp[(1) - (1)].variableType) == BIT_ARR){ (yyval.otype) = OutType::BOOL_ARR;}
    if( (yyvsp[(1) - (1)].variableType) == FLOAT_ARR){ (yyval.otype) = OutType::FLOAT_ARR;}
}
    break;

  case 33:

/* Line 1806 of yacc.c  */
#line 266 "InputParser/InputParser.yy"
    { 
    (yyval.otype) = OutType::getTuple(*(yyvsp[(1) - (1)].strConst));
}
    break;

  case 34:

/* Line 1806 of yacc.c  */
#line 270 "InputParser/InputParser.yy"
    {/* Empty */  (yyval.tVector) = new vector<OutType*>(); }
    break;

  case 35:

/* Line 1806 of yacc.c  */
#line 271 "InputParser/InputParser.yy"
    {
    (yyvsp[(1) - (2)].tVector)->push_back( (yyvsp[(2) - (2)].otype) );
    (yyval.tVector) = (yyvsp[(1) - (2)].tVector);
}
    break;

  case 36:

/* Line 1806 of yacc.c  */
#line 276 "InputParser/InputParser.yy"
    {
//add type
    OutType::makeTuple(*(yyvsp[(1) - (4)].strConst), *(yyvsp[(3) - (4)].tVector));

}
    break;

  case 37:

/* Line 1806 of yacc.c  */
#line 282 "InputParser/InputParser.yy"
    { /* Empty */ }
    break;

  case 38:

/* Line 1806 of yacc.c  */
#line 283 "InputParser/InputParser.yy"
    { }
    break;

  case 39:

/* Line 1806 of yacc.c  */
#line 285 "InputParser/InputParser.yy"
    {/* Empty */}
    break;

  case 40:

/* Line 1806 of yacc.c  */
#line 286 "InputParser/InputParser.yy"
    { }
    break;

  case 41:

/* Line 1806 of yacc.c  */
#line 290 "InputParser/InputParser.yy"
    {
	(yyval.bdag) = envt->prepareMiter(envt->getCopy(*(yyvsp[(3) - (3)].strConst)),  envt->getCopy(*(yyvsp[(1) - (3)].strConst)));
}
    break;

  case 42:

/* Line 1806 of yacc.c  */
#line 294 "InputParser/InputParser.yy"
    {solution.restart();}
    break;

  case 43:

/* Line 1806 of yacc.c  */
#line 295 "InputParser/InputParser.yy"
    {
	int tt = envt->assertDAG((yyvsp[(3) - (4)].bdag), std::cout);
	envt->printControls("");
	solution.stop();
	cout<<"COMPLETED"<<endl;
	if(tt != 0){
		return tt;
	}
}
    break;

  case 44:

/* Line 1806 of yacc.c  */
#line 305 "InputParser/InputParser.yy"
    {
	int tt = envt->runCommand(*(yyvsp[(1) - (5)].strConst), *(yyvsp[(3) - (5)].sList));
	delete (yyvsp[(1) - (5)].strConst);
	delete (yyvsp[(3) - (5)].sList);
	if(tt >= 0){
		return tt;
	}
}
    break;

  case 45:

/* Line 1806 of yacc.c  */
#line 314 "InputParser/InputParser.yy"
    {
	(yyval.sList) = new list<string*>();	
}
    break;

  case 46:

/* Line 1806 of yacc.c  */
#line 317 "InputParser/InputParser.yy"
    {
	(yyval.sList) = (yyvsp[(2) - (2)].sList);
	(yyval.sList)->push_back( (yyvsp[(1) - (2)].strConst));
}
    break;

  case 47:

/* Line 1806 of yacc.c  */
#line 321 "InputParser/InputParser.yy"
    {
	(yyval.sList) = (yyvsp[(2) - (2)].sList);
	(yyval.sList)->push_back( (yyvsp[(1) - (2)].strConst));
}
    break;

  case 48:

/* Line 1806 of yacc.c  */
#line 327 "InputParser/InputParser.yy"
    { /* Empty */ }
    break;

  case 49:

/* Line 1806 of yacc.c  */
#line 328 "InputParser/InputParser.yy"
    { /* */ }
    break;

  case 50:

/* Line 1806 of yacc.c  */
#line 331 "InputParser/InputParser.yy"
    {  (yyval.intConst)=0;  /* */ }
    break;

  case 51:

/* Line 1806 of yacc.c  */
#line 332 "InputParser/InputParser.yy"
    {
	currentBD->alias( *(yyvsp[(1) - (4)].strConst), (yyvsp[(3) - (4)].bnode));
	delete (yyvsp[(1) - (4)].strConst);
}
    break;

  case 52:

/* Line 1806 of yacc.c  */
#line 336 "InputParser/InputParser.yy"
    {

	list<string*>* childs = (yyvsp[(2) - (11)].sList);
	list<string*>::reverse_iterator it = childs->rbegin();
	
	list<bool_node*>* oldchilds = (yyvsp[(4) - (11)].nList);
	list<bool_node*>::reverse_iterator oldit = oldchilds->rbegin();
	
	bool_node* rhs;
	rhs = (yyvsp[(10) - (11)].bnode);
	int bigN = childs->size();
	Assert( bigN == oldchilds->size(), "This can't happen");	

	for(int i=0; i<bigN; ++i, ++it, ++oldit){		
		ARRASS_node* an = dynamic_cast<ARRASS_node*>(newNode(bool_node::ARRASS));
		an->multi_mother.reserve(2);
		an->multi_mother.push_back(*oldit);			
		an->multi_mother.push_back(rhs);
		Assert( rhs != NULL, "AAARRRGH This shouldn't happen !!");
		Assert((yyvsp[(7) - (11)].bnode) != NULL, "1: THIS CAN'T HAPPEN!!");
		an->quant = i;		
		currentBD->alias( *(*it), currentBD->new_node((yyvsp[(7) - (11)].bnode),  NULL,  an) );
		delete *it;
	}
	delete childs;
	delete oldchilds;	
}
    break;

  case 53:

/* Line 1806 of yacc.c  */
#line 364 "InputParser/InputParser.yy"
    {
	Assert(false, "UNREACHABLE");
	currentBD->create_outputs(2 /*NINPUTS*/, (yyvsp[(3) - (4)].bnode), *(yyvsp[(1) - (4)].strConst));
	delete (yyvsp[(1) - (4)].strConst);
}
    break;

  case 54:

/* Line 1806 of yacc.c  */
#line 369 "InputParser/InputParser.yy"
    {
  if ((yyvsp[(2) - (3)].bnode)) {
    /* Asserting an expression, construct assert node. */
    
    currentBD->new_node ((yyvsp[(2) - (3)].bnode), NULL, bool_node::ASSERT);
  }
}
    break;

  case 55:

/* Line 1806 of yacc.c  */
#line 376 "InputParser/InputParser.yy"
    {
  if ((yyvsp[(2) - (5)].bnode)) {
    /* Asserting an expression, construct assert node. */
	if(!((yyvsp[(2) - (5)].bnode)->type == bool_node::CONST && dynamic_cast<CONST_node*>((yyvsp[(2) - (5)].bnode))->getVal() == 1)){
		ASSERT_node* bn = dynamic_cast<ASSERT_node*>(newNode(bool_node::ASSERT));
		bn->setMsg(*(yyvsp[(4) - (5)].strConst));
		currentBD->new_node ((yyvsp[(2) - (5)].bnode), NULL, bn);
	}    
    delete (yyvsp[(4) - (5)].strConst);
  }
}
    break;

  case 56:

/* Line 1806 of yacc.c  */
#line 387 "InputParser/InputParser.yy"
    {
  if ((yyvsp[(2) - (4)].bnode)) {
    /* Asserting an expression, construct assert node. */
	if(!((yyvsp[(2) - (4)].bnode)->type == bool_node::CONST && dynamic_cast<CONST_node*>((yyvsp[(2) - (4)].bnode))->getVal() == 1)){
		ASSERT_node* bn = dynamic_cast<ASSERT_node*>(newNode(bool_node::ASSERT));
		bn->makeAssume();
		if ((yyvsp[(3) - (4)].strConst)) {
			bn->setMsg(*(yyvsp[(3) - (4)].strConst));
		}
		currentBD->new_node ((yyvsp[(2) - (4)].bnode), NULL, bn);
	}
	if ((yyvsp[(3) - (4)].strConst)) {
		delete (yyvsp[(3) - (4)].strConst);
	}
  }
}
    break;

  case 57:

/* Line 1806 of yacc.c  */
#line 404 "InputParser/InputParser.yy"
    { (yyval.strConst) = (yyvsp[(2) - (2)].strConst); }
    break;

  case 58:

/* Line 1806 of yacc.c  */
#line 405 "InputParser/InputParser.yy"
    { (yyval.strConst) = 0; }
    break;

  case 59:

/* Line 1806 of yacc.c  */
#line 407 "InputParser/InputParser.yy"
    { (yyval.bnode) = (yyvsp[(1) - (1)].bnode); }
    break;

  case 60:

/* Line 1806 of yacc.c  */
#line 408 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::AND);	
}
    break;

  case 61:

/* Line 1806 of yacc.c  */
#line 411 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::AND);
}
    break;

  case 62:

/* Line 1806 of yacc.c  */
#line 414 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::OR);	
}
    break;

  case 63:

/* Line 1806 of yacc.c  */
#line 417 "InputParser/InputParser.yy"
    { 	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::OR);	
}
    break;

  case 64:

/* Line 1806 of yacc.c  */
#line 420 "InputParser/InputParser.yy"
    {	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::XOR);	
}
    break;

  case 65:

/* Line 1806 of yacc.c  */
#line 423 "InputParser/InputParser.yy"
    {	
	bool_node* tmp = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::EQ);
	(yyval.bnode) = currentBD->new_node (tmp, NULL, bool_node::NOT);	
}
    break;

  case 66:

/* Line 1806 of yacc.c  */
#line 427 "InputParser/InputParser.yy"
    { 			
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::EQ);
}
    break;

  case 67:

/* Line 1806 of yacc.c  */
#line 430 "InputParser/InputParser.yy"
    {	
	(yyval.bnode) = currentBD->new_node((yyvsp[(3) - (4)].bnode), currentBD->get_node(*(yyvsp[(1) - (4)].strConst)), bool_node::ARR_R);	
	delete (yyvsp[(1) - (4)].strConst);
}
    break;

  case 68:

/* Line 1806 of yacc.c  */
#line 434 "InputParser/InputParser.yy"
    {
   
	//TUPLE_R_node* tn = dynamic_cast<TUPLE_R_node*>();
    
	//tn->idx = $4;
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (5)].bnode), (yyvsp[(4) - (5)].intConst));
	
}
    break;

  case 69:

/* Line 1806 of yacc.c  */
#line 442 "InputParser/InputParser.yy"
    {	
	(yyval.bnode) = currentBD->new_node((yyvsp[(3) - (4)].bnode), currentBD->create_const((yyvsp[(1) - (4)].intConst)), bool_node::ARR_R);		
}
    break;

  case 70:

/* Line 1806 of yacc.c  */
#line 445 "InputParser/InputParser.yy"
    {
	ARR_W_node* an = dynamic_cast<ARR_W_node*>(newNode(bool_node::ARR_W));
	an->multi_mother.push_back( currentBD->get_node(*(yyvsp[(1) - (8)].strConst)) );
	an->multi_mother.push_back( (yyvsp[(6) - (8)].bnode) );
	(yyval.bnode) = currentBD->new_node((yyvsp[(4) - (8)].bnode), NULL, an);	
	delete (yyvsp[(1) - (8)].strConst);
}
    break;

  case 71:

/* Line 1806 of yacc.c  */
#line 452 "InputParser/InputParser.yy"
    {
	ARR_W_node* an = dynamic_cast<ARR_W_node*>(newNode(bool_node::ARR_W));
	an->multi_mother.push_back( currentBD->create_const((yyvsp[(1) - (8)].intConst)) );
	an->multi_mother.push_back( (yyvsp[(6) - (8)].bnode) );
	(yyval.bnode) = currentBD->new_node((yyvsp[(4) - (8)].bnode), NULL, an);		
}
    break;

  case 72:

/* Line 1806 of yacc.c  */
#line 458 "InputParser/InputParser.yy"
    {
	int pushval = 0;
	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::ARRACC));
	list<bool_node*>* childs = (yyvsp[(2) - (6)].nList);
	list<bool_node*>::reverse_iterator it = childs->rbegin();
	int bigN = childs->size();
	an->multi_mother.reserve(bigN);
	for(int i=0; i<bigN; ++i, ++it){
		an->multi_mother.push_back(*it);
	}		
	Assert((yyvsp[(5) - (6)].bnode) != NULL, "2: THIS CAN'T HAPPEN!!");	
	(yyval.bnode) = currentBD->new_node((yyvsp[(5) - (6)].bnode), NULL,  an);
	delete childs;	
}
    break;

  case 73:

/* Line 1806 of yacc.c  */
#line 472 "InputParser/InputParser.yy"
    {
	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::ARR_CREATE));
    

	list<bool_node*>* childs = (yyvsp[(2) - (3)].nList);
	list<bool_node*>::reverse_iterator it = childs->rbegin();
	int bigN = childs->size();
	an->multi_mother.reserve(bigN);
	for(int i=0; i<bigN; ++i, ++it){
		an->multi_mother.push_back(*it);
	}		
	(yyval.bnode) = currentBD->new_node(NULL, NULL, an); 
	delete childs;
}
    break;

  case 74:

/* Line 1806 of yacc.c  */
#line 486 "InputParser/InputParser.yy"
    {

	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::TUPLE_CREATE));

	list<bool_node*>* childs = (yyvsp[(5) - (6)].nList);
	list<bool_node*>::reverse_iterator it = childs->rbegin();
	int bigN = childs->size();
	an->multi_mother.reserve(bigN);
	for(int i=0; i<bigN; ++i, ++it){
		an->multi_mother.push_back(*it);
	}
    (dynamic_cast<TUPLE_CREATE_node*>(an))->setName(*(yyvsp[(2) - (6)].strConst));
	(yyval.bnode) = currentBD->new_node(NULL, NULL, an); 
	delete childs;
}
    break;

  case 75:

/* Line 1806 of yacc.c  */
#line 501 "InputParser/InputParser.yy"
    {
	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::ACTRL));
	list<bool_node*>* childs = (yyvsp[(2) - (3)].nList);
	list<bool_node*>::reverse_iterator it = childs->rbegin();
	int bigN = childs->size();
	an->multi_mother.reserve(bigN);
	for(int i=0; i<bigN; ++i, ++it){
		an->multi_mother.push_back(*it);
	}		
	(yyval.bnode) = currentBD->new_node(NULL, NULL, an); 
	delete childs;
}
    break;

  case 76:

/* Line 1806 of yacc.c  */
#line 514 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::PLUS); 	
}
    break;

  case 77:

/* Line 1806 of yacc.c  */
#line 518 "InputParser/InputParser.yy"
    {	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::DIV); 	
}
    break;

  case 78:

/* Line 1806 of yacc.c  */
#line 522 "InputParser/InputParser.yy"
    {	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::MOD); 	
}
    break;

  case 79:

/* Line 1806 of yacc.c  */
#line 526 "InputParser/InputParser.yy"
    {
	(yyval.bnode)= currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::TIMES);
}
    break;

  case 80:

/* Line 1806 of yacc.c  */
#line 529 "InputParser/InputParser.yy"
    {
	bool_node* neg1 = currentBD->new_node((yyvsp[(3) - (3)].bnode), NULL, bool_node::NEG);
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode), neg1, bool_node::PLUS); 	
}
    break;

  case 81:

/* Line 1806 of yacc.c  */
#line 533 "InputParser/InputParser.yy"
    {
	
	(yyval.bnode) = currentBD->new_node((yyvsp[(3) - (3)].bnode), (yyvsp[(1) - (3)].bnode), bool_node::LT);     
}
    break;

  case 82:

/* Line 1806 of yacc.c  */
#line 537 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode), (yyvsp[(3) - (3)].bnode), bool_node::LT);
}
    break;

  case 83:

/* Line 1806 of yacc.c  */
#line 540 "InputParser/InputParser.yy"
    {
	bool_node* tmp = currentBD->new_node((yyvsp[(1) - (3)].bnode), (yyvsp[(3) - (3)].bnode), bool_node::LT);
	(yyval.bnode) = currentBD->new_node(tmp, NULL, bool_node::NOT);
}
    break;

  case 84:

/* Line 1806 of yacc.c  */
#line 544 "InputParser/InputParser.yy"
    {
	bool_node* tmp = currentBD->new_node((yyvsp[(3) - (3)].bnode), (yyvsp[(1) - (3)].bnode), bool_node::LT);
	(yyval.bnode) = currentBD->new_node(tmp, NULL, bool_node::NOT);
}
    break;

  case 85:

/* Line 1806 of yacc.c  */
#line 548 "InputParser/InputParser.yy"
    {
	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::ARRACC));
	bool_node* yesChild =((yyvsp[(3) - (5)].bnode));
	bool_node* noChild = ((yyvsp[(5) - (5)].bnode));
	an->multi_mother.push_back( noChild );
	an->multi_mother.push_back( yesChild );	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (5)].bnode), NULL, an); 	
}
    break;

  case 86:

/* Line 1806 of yacc.c  */
#line 559 "InputParser/InputParser.yy"
    { /* Empty */  	(yyval.nList) = new list<bool_node*>();	}
    break;

  case 87:

/* Line 1806 of yacc.c  */
#line 560 "InputParser/InputParser.yy"
    {

//The signs are already in the stack by default. All I have to do is not remove them.
	if((yyvsp[(1) - (2)].bnode) != NULL){
		(yyvsp[(2) - (2)].nList)->push_back( (yyvsp[(1) - (2)].bnode) );
	}else{
		(yyvsp[(2) - (2)].nList)->push_back( NULL );
	}
	(yyval.nList) = (yyvsp[(2) - (2)].nList);
}
    break;

  case 88:

/* Line 1806 of yacc.c  */
#line 571 "InputParser/InputParser.yy"
    {
	(yyval.sList) = new list<string*>();	
	(yyval.sList)->push_back( (yyvsp[(1) - (1)].strConst));
}
    break;

  case 89:

/* Line 1806 of yacc.c  */
#line 575 "InputParser/InputParser.yy"
    {
	(yyval.sList) = (yyvsp[(2) - (2)].sList);
	(yyval.sList)->push_back( (yyvsp[(1) - (2)].strConst));
}
    break;

  case 90:

/* Line 1806 of yacc.c  */
#line 580 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_const((yyvsp[(1) - (1)].intConst));
}
    break;

  case 91:

/* Line 1806 of yacc.c  */
#line 583 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_const((yyvsp[(1) - (1)].doubleConst));
}
    break;

  case 92:

/* Line 1806 of yacc.c  */
#line 587 "InputParser/InputParser.yy"
    {
    
	list<bool_node*>* params = (yyvsp[(7) - (16)].nList);
	if(false && params->size() == 0){

        (yyval.bnode) = currentBD->create_inputs(-1,OutType::getTuple(*(yyvsp[(4) - (16)].strConst)), *(yyvsp[(1) - (16)].strConst));

		delete (yyvsp[(1) - (16)].strConst);
	}else{	
		string& fname = *(yyvsp[(1) - (16)].strConst);
		list<bool_node*>::reverse_iterator parit = params->rbegin();
		UFUN_node* ufun = new UFUN_node(fname);
		ufun->outname = *(yyvsp[(13) - (16)].strConst);
		int fgid = (yyvsp[(15) - (16)].intConst);
		ufun->fgid = fgid;	
		bool_node* pCond;	
		if(currentBD->methdparams.count(fgid)>0){
			ufun->multi_mother = currentBD->methdparams[fgid];
			ufun->makeDependent();
			pCond = currentBD->create_const(1);
		}else{
			for( ; parit != params->rend(); ++parit){
				ufun->multi_mother.push_back((*parit));
			}
			pCond = (yyvsp[(10) - (16)].bnode);
		}
		

        ufun->set_nbits( 0 );
        ufun->set_tupleName(*(yyvsp[(4) - (16)].strConst));
		
		
		//ufun->name = (currentBD->new_name(fname));
		(yyval.bnode) = currentBD->new_node(pCond, NULL, ufun);
		if(currentBD->methdparams.count(fgid)==0){
			currentBD->methdparams[fgid].push_back((yyval.bnode));
		}
		
		
		delete (yyvsp[(1) - (16)].strConst);
		delete (yyvsp[(13) - (16)].strConst);
	}
	delete (yyvsp[(7) - (16)].nList);

}
    break;

  case 93:

/* Line 1806 of yacc.c  */
#line 632 "InputParser/InputParser.yy"
    {
	
	list<bool_node*>* params = (yyvsp[(6) - (15)].nList);
	if(false && params->size() == 0){
		if( (yyvsp[(3) - (15)].variableType) == INT){
			(yyval.bnode) = currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT , *(yyvsp[(1) - (15)].strConst)); 
		}else{
			if((yyvsp[(3) - (15)].variableType)==FLOAT){
				(yyval.bnode) = currentBD->create_inputs(-1,OutType::FLOAT, *(yyvsp[(1) - (15)].strConst));
			}else{
				(yyval.bnode) = currentBD->create_inputs(-1,OutType::BOOL, *(yyvsp[(1) - (15)].strConst));
			}
		}
		delete (yyvsp[(1) - (15)].strConst);
	}else{	
		string& fname = *(yyvsp[(1) - (15)].strConst);
		list<bool_node*>::reverse_iterator parit = params->rbegin();
		UFUN_node* ufun = new UFUN_node(fname);
		ufun->outname = *(yyvsp[(12) - (15)].strConst);
		int fgid = (yyvsp[(14) - (15)].intConst);
		ufun->fgid = fgid;	
		bool_node* pCond;	
		if(currentBD->methdparams.count(fgid)>0){
			ufun->multi_mother = currentBD->methdparams[fgid];
			ufun->makeDependent();
			pCond = currentBD->create_const(1);
		}else{
			for( ; parit != params->rend(); ++parit){
				ufun->multi_mother.push_back((*parit));
			}
			pCond = (yyvsp[(9) - (15)].bnode);
		}
		
		if( (yyvsp[(3) - (15)].variableType) == INT || (yyvsp[(3) - (15)].variableType)==INT_ARR){
			ufun->set_nbits( 2 /*NINPUTS*/  );
		}else{	
			ufun->set_nbits( 1  );
		}
		if((yyvsp[(3) - (15)].variableType) == INT_ARR || (yyvsp[(3) - (15)].variableType)==BIT_ARR){
			ufun->makeArr();
		}
		
		//ufun->name = (currentBD->new_name(fname));
		(yyval.bnode) = currentBD->new_node(pCond, NULL, ufun);
		if(currentBD->methdparams.count(fgid)==0){
			currentBD->methdparams[fgid].push_back((yyval.bnode));
		}
		
		
		delete (yyvsp[(1) - (15)].strConst);
		delete (yyvsp[(12) - (15)].strConst);
	}
	delete (yyvsp[(6) - (15)].nList);
}
    break;

  case 94:

/* Line 1806 of yacc.c  */
#line 687 "InputParser/InputParser.yy"
    {		
		(yyval.bnode) = currentBD->new_node((yyvsp[(2) - (2)].bnode), NULL, bool_node::NEG);				
}
    break;

  case 95:

/* Line 1806 of yacc.c  */
#line 690 "InputParser/InputParser.yy"
    { 
	(yyval.bnode) = currentBD->new_node((yyvsp[(2) - (2)].bnode), NULL, bool_node::NOT);		    
}
    break;

  case 96:

/* Line 1806 of yacc.c  */
#line 694 "InputParser/InputParser.yy"
    { 
						(yyval.bnode) = (yyvsp[(2) - (3)].bnode); 
						}
    break;

  case 97:

/* Line 1806 of yacc.c  */
#line 697 "InputParser/InputParser.yy"
    { 			
			(yyval.bnode) = currentBD->get_node(*(yyvsp[(1) - (1)].strConst));
			delete (yyvsp[(1) - (1)].strConst);				
			 
		}
    break;

  case 98:

/* Line 1806 of yacc.c  */
#line 702 "InputParser/InputParser.yy"
    {		
	(yyval.bnode) = currentBD->create_controls(-1, *(yyvsp[(2) - (3)].strConst));
	delete (yyvsp[(2) - (3)].strConst);
}
    break;

  case 99:

/* Line 1806 of yacc.c  */
#line 706 "InputParser/InputParser.yy"
    {
	int nctrls = (yyvsp[(3) - (4)].intConst);
	if(overrideNCtrls){
		nctrls = NCTRLS;
	}
	(yyval.bnode) = currentBD->create_controls(nctrls, *(yyvsp[(2) - (4)].strConst));
	delete (yyvsp[(2) - (4)].strConst);
}
    break;

  case 100:

/* Line 1806 of yacc.c  */
#line 714 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_controls((yyvsp[(3) - (5)].intConst), *(yyvsp[(2) - (5)].strConst));
	delete (yyvsp[(2) - (5)].strConst);

}
    break;

  case 101:

/* Line 1806 of yacc.c  */
#line 719 "InputParser/InputParser.yy"
    {		
	(yyval.bnode) = currentBD->create_controls(-1, *(yyvsp[(3) - (4)].strConst), true);
	delete (yyvsp[(3) - (4)].strConst);
}
    break;

  case 102:

/* Line 1806 of yacc.c  */
#line 723 "InputParser/InputParser.yy"
    {
	int nctrls = (yyvsp[(4) - (5)].intConst);
	if(overrideNCtrls){
		nctrls = NCTRLS;
	}
	(yyval.bnode) = currentBD->create_controls(nctrls, *(yyvsp[(3) - (5)].strConst), true);
	delete (yyvsp[(3) - (5)].strConst);
}
    break;

  case 103:

/* Line 1806 of yacc.c  */
#line 731 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_controls((yyvsp[(4) - (6)].intConst), *(yyvsp[(3) - (6)].strConst), true);
	delete (yyvsp[(3) - (6)].strConst);

}
    break;

  case 104:

/* Line 1806 of yacc.c  */
#line 738 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (1)].intConst); }
    break;

  case 105:

/* Line 1806 of yacc.c  */
#line 739 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (3)].intConst) + (yyvsp[(3) - (3)].intConst); }
    break;

  case 106:

/* Line 1806 of yacc.c  */
#line 740 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (3)].intConst) - (yyvsp[(3) - (3)].intConst); }
    break;

  case 107:

/* Line 1806 of yacc.c  */
#line 742 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (1)].intConst); }
    break;

  case 108:

/* Line 1806 of yacc.c  */
#line 743 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(2) - (3)].intConst); }
    break;

  case 109:

/* Line 1806 of yacc.c  */
#line 744 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (3)].intConst) * (yyvsp[(3) - (3)].intConst); }
    break;

  case 110:

/* Line 1806 of yacc.c  */
#line 745 "InputParser/InputParser.yy"
    { Assert( (yyvsp[(3) - (3)].intConst) != 0, "You are attempting to divide by zero !!");
							      (yyval.intConst) = (yyvsp[(1) - (3)].intConst) / (yyvsp[(3) - (3)].intConst); }
    break;

  case 111:

/* Line 1806 of yacc.c  */
#line 747 "InputParser/InputParser.yy"
    { Assert( (yyvsp[(3) - (3)].intConst) != 0, "You are attempting to mod by zero !!");
							      (yyval.intConst) = (yyvsp[(1) - (3)].intConst) % (yyvsp[(3) - (3)].intConst); }
    break;

  case 112:

/* Line 1806 of yacc.c  */
#line 751 "InputParser/InputParser.yy"
    {  (yyval.intConst) = (yyvsp[(1) - (1)].intConst); }
    break;

  case 113:

/* Line 1806 of yacc.c  */
#line 752 "InputParser/InputParser.yy"
    {  (yyval.intConst) = -(yyvsp[(2) - (2)].intConst); }
    break;

  case 114:

/* Line 1806 of yacc.c  */
#line 755 "InputParser/InputParser.yy"
    {  (yyval.intConst) = (yyvsp[(1) - (1)].intConst); }
    break;

  case 115:

/* Line 1806 of yacc.c  */
#line 756 "InputParser/InputParser.yy"
    { (yyval.intConst) = 1; }
    break;

  case 116:

/* Line 1806 of yacc.c  */
#line 757 "InputParser/InputParser.yy"
    { (yyval.intConst) = 0; }
    break;

  case 117:

/* Line 1806 of yacc.c  */
#line 759 "InputParser/InputParser.yy"
    { (yyval.strConst)=(yyvsp[(1) - (1)].strConst); }
    break;



/* Line 1806 of yacc.c  */
#line 2973 "InputParser.cpp"
      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = (char *) YYSTACK_ALLOC (yymsg_alloc);
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
	 error, discard it.  */

      if (yychar <= YYEOF)
	{
	  /* Return failure if at end of input.  */
	  if (yychar == YYEOF)
	    YYABORT;
	}
      else
	{
	  yydestruct ("Error: discarding",
		      yytoken, &yylval);
	  yychar = YYEMPTY;
	}
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule which action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;


      yydestruct ("Error: popping",
		  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  *++yyvsp = yylval;


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined(yyoverflow) || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule which action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
		  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  /* Make sure YYID is used.  */
  return YYID (yyresult);
}



/* Line 2067 of yacc.c  */
#line 761 "InputParser/InputParser.yy"



void Inityyparse(){

	 	
}

void yyerror(const char* c){
	Assert(false, (char *)c); 
}


int isatty(int i){



return 1;
}

