package snow.zhen.zayta.main.sokoban.map;


import com.badlogic.gdx.files.FileHandle;

import snow.zhen.zayta.main.assets.Files;

public class Levels {


    private String [] levels;
    public Levels(){
        loadFile(Files.puzzles[1]);
    }

    class Level{
        private int width, height;
        private String lvlData;
        private Level(String [] parsedLvl){

            height = parsedLvl.length;
            //mapWidth is the length of longest string in parsedLvl
            width = parsedLvl[0].length();
            for(String s: parsedLvl){
                if(s.length()>width){
                    width = s.length();
                }
            }
            lvlData = new String();
            for(String l: parsedLvl) {
                lvlData += l;
                //adds empty space for rows to fulfill width rq
                for(int i = width-l.length();i>0;i--){
                    lvlData+=" ";
                }
            }

        }
        public String getLvlData(){
            return lvlData;
        }
        public int getWidth(){
            return width;
        }
        public int getHeight(){
            return height;
        }
    }

    public Level getLevel(int lvl){
        String [] parsedLvl = levels[lvl%levels.length].split("\n");

        return new Level(parsedLvl);

    }


    private void loadFile(FileHandle puzzleFile){
        String levelsString = puzzleFile.readString();
        levelsString = levelsString. replaceAll("[0-9]","");
        levels = levelsString.split(";");

    }
    
}
