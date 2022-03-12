package jrails;

import java.util.List;
import java.util.*;
import java.lang.reflect.*;
import java.lang.annotation.*;
import java.io.*;

public class Model {

    static File file;
    static FileWriter writer;
    static int max_id;
    int id = 0;

    public Model() {
        if (writer == null && file == null) {
            Class<?> c = this.getClass();
            file = new File(c.getName() + ".txt");
            try {
                file.createNewFile();
                writer = new FileWriter(file);

                for (Field f : c.getDeclaredFields()) {
                    Annotation[] annotations = f.getDeclaredAnnotations();
                    if (annotations.length != 0 && annotations[0] instanceof Column) {

                        writer.write("|" + f.getName() + ":" + f.getType().getName() + "|");
                        System.out.println(f.getName() + ":" + f.getType().getName() + "|");

                    }
                }

                writer.write("\n");
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }

        }

    }

    public Model(int id) {
        Class<?> c = this.getClass();
        this.id = id;
        try {

            file = new File(c.getName() + ".txt");
            writer = new FileWriter(file);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    public void save() {
        try {
            if (id == 0) {
                max_id++;
                id = max_id;
                writer.write(id + "^");
                Class<?> c = this.getClass();
                for (Field f : c.getDeclaredFields()) {
                    Object value = f.get(this);
                    writer.write(value + "^");
                }
                writer.write("\n");
                writer.flush();
            } else {
                Scanner scanner = new Scanner(file);
                ArrayList<String> lines = new ArrayList<String>();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith(id + "^")) {
                        line = id + "^";
                        for (Field f : this.getClass().getDeclaredFields()) {
                            Object value = f.get(this);
                            line += value + "^";
                        }
                    }
                    lines.add(line);
                }
                file.delete();
                scanner.close();

                Class<?> c = this.getClass();
                file = new File(c.getName() + ".txt");

                file.createNewFile();
                writer = new FileWriter(file);

                for (String s : lines) {
                    writer.write(s + "\n");

                }
                writer.flush();

            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public int id() {
        return this.id;
    }

    public static <T> T find(Class<T> c, int id) {

        List<T> res = all(c);
        for (T t : res) {
            Field[] fs = t.getClass().getSuperclass().getDeclaredFields();
            for (Field f : fs) {
                if (f.getName().equals("id")) {
                    f.setAccessible(true);
                    try {

                        if (id == (int) f.get(t)) {
                            return t;
                        }

                    } catch (Exception e) {
                        e.printStackTrace(System.out);
                    }
                }
            }
        }
        return null;
    }

    public static <T> List<T> all(Class<T> c) {
        // Returns a List<element type>
        List<T> res = new ArrayList<T>();
        try {

            Scanner scanner = new Scanner(file);
            ArrayList<String> lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
            for (String l : lines) {
                if (!l.startsWith("|")) {
                    String[] ls = l.split("\\^");
                    T t = c.getDeclaredConstructor().newInstance();

                    Field[] fs = t.getClass().getSuperclass().getDeclaredFields();
                    for (Field f : fs) {
                        if (f.getName().equals("id")) {
                            f.setAccessible(true);
                            f.set(t, Integer.parseInt(ls[0]));
                        }
                    }

                    Field[] fields = c.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        if (fields[i].getType().getName().equals("java.lang.String")) {
                            fields[i].set(t, ls[i + 1]);
                        } else if (fields[i].getType().getName().equals("int")) {
                            fields[i].set(t, Integer.parseInt(ls[i + 1]));
                        } else {
                            fields[i].set(t, Boolean.parseBoolean(ls[i + 1]));
                        }
                    }
                    res.add(t);

                }

            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return res;

    }

    public void destroy() {
        try {
            Scanner scanner = new Scanner(file);
            ArrayList<String> lines = new ArrayList<String>();
            boolean flag = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith(id + "^")) {
                    lines.add(line);
                }
                else{
                    flag = true;
                }
            }
            if(!flag){
                scanner.close();
                throw new UnsupportedOperationException();
            }
            file.delete();
            scanner.close();

            Class<?> c = this.getClass();
            file = new File(c.getName() + ".txt");

            file.createNewFile();
            writer = new FileWriter(file);

            for (String s : lines) {
                writer.write(s + "\n");

            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new UnsupportedOperationException();
        }

    }

    public static void reset() {
        try {
            file.createNewFile();
            writer = new FileWriter(file);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}



