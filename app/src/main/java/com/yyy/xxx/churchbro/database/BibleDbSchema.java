package com.yyy.xxx.churchbro.database;

/**
 * Created by len on 2017. 2. 14..
 */

public class BibleDbSchema {

    public static final class BibleVersionTable {

        public static final String NAME = "bible_version_key";

        public static final class Cols {
            public static final String TABLE = "table";
            public static final String ABBREVIATION = "abbreviation";
            public static final String VERSION = "version";
        }
    }

    public static final class BibleName {

        public static final String NAME = "key_english";

        /**
         *  b는 숫자로 구성되어 있고, n은 성경 이름이 써져있다.
         *  ex) 1 -> Genesis
         */
        public static final class Cols {
            public static final String NUMBER = "b";
            public static final String BIBLENAME = "n";
        }
    }

    public static final class BibleText {

        public static final String NAME = "t_kjv";

        /**
         *  b는 성경 제목에 대한 열
         *  c는 성경 장 에 대한 열
         *  v는 성경 절에 대한 열
         *  t는 해당 내용에 대한 텍스트.
         */
        public static final class Cols{
            public static final String BIBLENAME = "b";
            public static final String BIBLECHAPTER = "c";
            public static final String BIBLEVERSE = "v";
            public static final String BIBLETEXT = "t";
        }
    }
}
