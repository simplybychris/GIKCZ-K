package CGlab;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Renderer {

    public enum LineAlgo {NAIVE, DDA, BRESENHAM, BRESENHAM_INT;}

    private BufferedImage render;
    private int h;
    private int w;

    private String filename;
    private LineAlgo lineAlgo;

    public Renderer(String filename, int w, int h, String alg) {
        this.w = w;
        this.h = h;
        render = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        this.filename = filename;
        if (alg.equals("LINE_NAIVE")) {
            lineAlgo = LineAlgo.NAIVE;
        }
        if (alg.equals("LINE_BRESENHAM")) {
            lineAlgo = LineAlgo.BRESENHAM;
        }
        if (alg.equals("LINE_BRESENHAM_INT")) {
            lineAlgo = LineAlgo.BRESENHAM_INT;
        }
    }

    public Vec3f barycentric(Vec2f A, Vec2f B, Vec2f C, Vec2f P) {
        Vec3f v1 = new Vec3f(B.x - A.x, C.x - A.x, A.x - P.x);
        // tutaj potrzebujemy wektora składającego się ze współrzędnych
        // x wektorów AB, AC ora PA.

        Vec3f v2 = new Vec3f(B.y - A.y,  C.y - A.y ,A.y - P.y);
        // tutaj potrzebujemy wektora składającego się ze współrzędnych
        // y wektorów AB, AC ora PA.

        Vec3f cross = cross(v1,v2);
        // iloczyn wektorowy v1 i v2. Wskazówka: zaimplementuj do tego oddzielną metodę
        // Vec2f uv = // wektor postaci: cross.x / cross.z, cross.y / cross.z

        Vec2f uv = new Vec2f((cross.x/cross.z),(cross.y/cross.z));
        // wektor postaci: cross.x / cross.z, cross.y / cross.z

        Vec3f barycentric = new Vec3f(uv.x,uv.y,1-uv.x-uv.y);
        // współrzędne barycentryczne, uv.x, uv.y, 1- uv.x - uv.y
        return barycentric;
    }

    public Vec3f cross(Vec3f A, Vec3f B){
        float newA = (A.y*B.z - A.z*B.y);
        float newB = (A.z*B.x - A.x*B.z);
        float newC = (A.x*B.y - A.y*B.x);
        return new Vec3f(newA, newB, newC);
    }

    public void drawTriangle(Vec2f A, Vec2f B, Vec2f C) {
        for(int y=h;y>0;y--){   // iteracja po y
            for(int x=0;x<w;x++){ // iteracja po x
                Vec2f P = new Vec2f(x, y); // punkt P(x,y)
                Vec3f bar = barycentric(A,B,C,P);

                        if(bar.x>0 && bar.x<1 && bar.y>0 && bar.y<1 && bar.z>0 && bar.z<1){
                            //jeśli każda ze współrzednych należy do przedziału (0,1) to rysuj pixel
                            drawPoint(x,y);
                        }
            }
        }
    }

    public void drawPoint(int x, int y) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
        render.setRGB(x, y, white);
    }

    public void drawLine(int x0, int y0, int x1, int y1) {
        if (lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1);
        if (lineAlgo == LineAlgo.DDA) drawLineDDA(x0, y0, x1, y1);
        if (lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1);
        if (lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1);
    }

    public void drawLineNaive(int x0, int y0, int x1, int y1) {

        int x;
        double dy = y1 - y0;
        double dx = x1 - x0;
        double m = dy / dx;
        double y = y0;

        for (x = x0; x <= x1; x++) {
            drawPoint(x, (int) Math.round(y));
            y = y + m;
        }
    }

    public void drawLineDDA(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj
    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

        int dx = x1 - x0;
        int dy = y1 - y0;
        float derr = Math.abs(dy / (float) (dx));
        float err = 0;

        int y = y0;

        for (int x = x0; x <= x1; x++) {
            render.setRGB(x, y, white);
            err += derr;
            if (err > 0.5) {
                y += (y1 > y0 ? 1 : -1);
                err -= 1.;
            }
        } // Oktanty: 7, 8
    }

    public void drawLineBresenhamInt(int x0, int y0, int x1, int y1) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

        int dx = x1 - x0;
        int dy = y1 - y0;
        int derr = 2 * dy - dx;

        int y = y0;

        for (int x = x0; x < x1; x++) {
            render.setRGB(x, y, white);
            if (derr > 0) {
                y++;
                derr = derr - 2 * dx;
            }
            derr = derr + 2 * dy;
        } // Oktanty: 8
    }

    public void save() throws IOException {
        File outputfile = new File(filename);
        render = Renderer.verticalFlip(render);
        ImageIO.write(render, "png", outputfile);
    }

    public void clear() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int black = 0 | (0 << 8) | (0 << 16) | (255 << 24);
                render.setRGB(x, y, black);
            }
        }
    }

    public static BufferedImage verticalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage flippedImage = new BufferedImage(w, h, img.getColorModel().getTransparency());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return flippedImage;
    }
}
