package constant;

import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.FontFormatException;

public class FontClass {
        
        public static Font FONT60;
        public static Font FONT50;
        public static Font FONT22;
        public static Font FONT28;

        static {
            try {
                FONT60 = Font.createFont(Font.TRUETYPE_FONT, new File("res/ui/RAINYHEARTS.TTF")).deriveFont(60f);
                FONT50 = Font.createFont(Font.TRUETYPE_FONT, new File("res/ui/RAINYHEARTS.TTF")).deriveFont(50f);
                FONT22 = Font.createFont(Font.TRUETYPE_FONT, new File("res/ui/RAINYHEARTS.TTF")).deriveFont(22f);
                FONT28 = Font.createFont(Font.TRUETYPE_FONT, new File("res/ui/RAINYHEARTS.TTF")).deriveFont(28f);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }
}
