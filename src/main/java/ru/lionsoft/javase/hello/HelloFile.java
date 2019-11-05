/*
 * File:    HelloFile.java
 * Project: HelloJavaSE
 * Date:    Jan 28, 2019 8:19:48 AM
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import ru.lionsoft.javase.hello.exception.HelloException;

/**
 * Примеры работы с файлами
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloFile {

    private static final Logger LOG = Logger.getLogger(HelloFile.class.getName());
    
    private static final String DIR_TEST = "/tmp/test";
    private static final String BINARY_FILENAME = "test.bin";
    private static final String TEXT_FILENAME = "test.txt";
    private static final String BOX_FILENAME = "box.bin";

    public static final String CHARSET_DOS = "cp866";
    public static final String CHARSET_WIN = "cp1251";
    public static final String CHARSET_ISO = "iso8859-5";
    public static final String CHARSET_KOI8R = "koi8-r";
    public static final String CHARSET_UTF8 = "utf-8";
    
    public static void main(String[] args) throws IOException {
        HelloFile app = new HelloFile();
        
        File dir = new File(DIR_TEST);
        if (dir.exists()) {
            System.out.println("Directory: " + DIR_TEST + " -> exists!");
        } else {
            System.out.println("Create directory: " + DIR_TEST + " -> " + dir.mkdirs());
        }
        
        File file = new File(dir, "file.txt");
        if (file.exists()) {
            System.out.println("File: " + file.getName() + " -> exists!");
        } else {
            System.out.println("Create new file: " + file.getName() + " -> " + file.createNewFile());
        }
        
        app.dir(dir);
        app.testMyException();
        
        // Binary file
        File binFile = new File(dir, BINARY_FILENAME);
        app.writeToBinaryFile(binFile);
        app.writeToBinaryFileBuffered(binFile);
        app.readFromBinaryFile(binFile);
        app.readFromBinaryFileBuffered(binFile);
        
        app.copyBinaryFile(binFile, new File(dir, BINARY_FILENAME + ".bak"));
        try {
            app.compressBinaryFile(DIR_TEST + "/" + BINARY_FILENAME);
            app.decompressBinaryFile(DIR_TEST + "/" + BINARY_FILENAME + ".gz");
        } catch (HelloException ex) {
            System.err.println("ex.message   = " + ex.getMessage());
            System.err.println("errorCode    = " + ex.getErrorCode());
            System.err.println("errorMessage = " + ex.getErrorMessage());
        }
        
        // Text file
        File txtFile = new File(dir, TEXT_FILENAME);
        app.writeToTextFile(txtFile);
        app.readFromTextFile(txtFile);
        app.writeToTextFile2(txtFile);
        app.readFromTextFile2(txtFile);
        
        // Object write/read (serialize/deserialize)
        File boxFile = new File(dir, BOX_FILENAME);
        Box box = new Box(1, 2, 3);
        app.writeObjectToFile(boxFile, box);
        Box boxRead = (Box) app.readObjectFromFile(boxFile);
        System.out.println("box = " + boxRead);
        
        // Random acces file
        app.randomAccessFile(binFile);
    }

    /**
     * Распечатать содержимое каталога (аналог команды dir/ls)
     * @param path путь к каталогу
     */
    public void dir(File path) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.dir()");
        if (path == null) return;
        if (!path.isDirectory()) return;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        System.out.println(" Содержимое папки " + path.getPath());

        long fileLengths = 0;
        int dirs = 0;
        int files = 0;

        for (File file : path.listFiles()) {
            if (file.isDirectory()) dirs++;
            if (file.isFile()) {
                fileLengths += file.length();
                files++;
            }
            System.out.printf("%s %s %c%c%c %,9d %s\n",
                    dateFormat.format(new Date(file.lastModified())),
                    (file.isDirectory() ? "<DIR>" : "     "),
                    (file.canRead() ? 'r' : '-'),
                    (file.canWrite()? 'w' : '-'),
                    (file.canExecute()? 'x' : '-'),
                    file.length(),
                    file.getName()
            );
        }
        System.out.printf("        %5d файлов %,15d байт\n", files, fileLengths);
        System.out.printf("        %5d папок  %,15d байт свободно\n", dirs, path.getFreeSpace());
    }

    /**
     * Тестирование работы своего исключения
     */
    public void testMyException() {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.testMyException()");
        try {
            throw new HelloException(1, "except!");
        } catch (HelloException ex) {
            System.err.println("ex.message   = " + ex.getMessage());
            System.err.println("errorCode    = " + ex.getErrorCode());
            System.err.println("errorMessage = " + ex.getErrorMessage());
        }
    }

    /**
     * Запись в бинарный файл
     * @param filename имя файла
     * @throws FileNotFoundException когда файл не найден
     * @throws IOException когда ошибка ввода/вывода
     */
    public void writeToBinaryFile(String filename) throws FileNotFoundException, IOException {
        writeToBinaryFile(new File(filename));
    }

    /**
     * Запись в бинарный файл
     * @param dir имя каталога
     * @param filename имя файла
     * @throws FileNotFoundException когда файл не найден
     * @throws IOException когда ошибка ввода/вывода
     */
    public void writeToBinaryFile(String dir, String filename) throws FileNotFoundException, IOException {
        writeToBinaryFile(new File(dir, filename));
    }

    /**
     * Запись в бинарный файл
     * @param file файл для записи
     * @throws FileNotFoundException когда файл не найден
     * @throws IOException когда ошибка ввода/вывода
     */
    public void writeToBinaryFile(File file) throws FileNotFoundException, IOException {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.writeToBinaryFile()");
        // 1. open file for write
        OutputStream out = new FileOutputStream(file);
        // 2. write into file
        for (int i = 0; i < 256; i++) {
            out.write(i);
        }
        byte[] buffer = new byte[256];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte)(255 - i);
        }
        out.write(buffer);
        // 3. close file
        out.close();
    }
    
    /**
     * Запись в юинарный файл используя массив (буфер)
     * @param file файл для записи
     */
    public void writeToBinaryFileBuffered(File file) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.writeToBinaryFileBuffered()");
        OutputStream out = null;
        try { 
            // 1. open file for write for append
            out = new FileOutputStream(file, true);
            // 2. write into file
            byte[] buffer = new byte[256];
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = (byte)(255 - i);
            }
            out.write(buffer);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            // 3. close file
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * Чтение бинарного файла
     * @param file файл для чтения
     */
    public void readFromBinaryFile(File file) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.readFromBinaryFile()");
        InputStream in = null;
        try {
            // 1. open file for read
            in = new FileInputStream(file);
            // 2. read from file
            int b;
            while ((b = in.read()) != -1) {
                byte bb = (byte)b;
                System.out.println(bb);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            // 3. close file
            try {
                in.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Чтение бинарорного файла используя массив (буфер)
     * @param file файл для чтения
     */
    public void readFromBinaryFileBuffered(File file) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.readFromBinaryFileBuffered()");
        try (
                // 1. open file for read
                InputStream in = new FileInputStream(file);
                Box box = new Box(1, 2, 3);
            ) {
            box.open();
            // 2. read from file
            byte[] buffer = new byte[35];
            int len;
            while ((len = in.read(buffer)) != -1) {
                for (int i = 0; i < len; i++) {
                    System.out.println(((int)buffer[i]) & 0xFF);
                }
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        // 3. Auto close file
    }
    
    /**
     * Копирование бинарного файла
     * @param srcFile исходный файл
     * @param destFile целевой файл
     * @throws IOException когда ошибка ввода/вывода 
     */
    public void copyBinaryFile(File srcFile, File destFile) throws IOException {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.copyBinaryFile()");
        try (
                // 1. open files
                InputStream in = new FileInputStream(srcFile);
                OutputStream out = new FileOutputStream(destFile);
            ) {
            // 2. copy file
            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
        // 3. auto close file
    }

    /**
     * Сжатие бинарного файла (метод сжатия GZIP)
     * @param filename имя файла
     * @throws HelloException моя ошибка при ошибке сжатия
     * @throws FileNotFoundException когда файл не найден
     */
    public void compressBinaryFile(String filename) throws HelloException, FileNotFoundException {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.compressBinaryFile()");
        try (
                // 1. open files
                InputStream in = new BufferedInputStream(new FileInputStream(filename));
                OutputStream out = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(filename + ".gz")));
            ) {
            // 2. copy file
            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw ex;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new HelloException(2, filename);
        }
        // 3. auto close file
    }
    
    /**
     * Разжатие бинарного файла (метод сжатия GZIP)
     * @param filename имя файла
     * @throws HelloException моя ошибка при ошибке сжатия
     * @throws FileNotFoundException когда файл не найден
     */
    public void decompressBinaryFile(String filename) throws HelloException, FileNotFoundException {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.decompressBinaryFile()");
        if (!filename.endsWith(".gz")) throw new HelloException(3, filename);
        final String destFilename = filename.substring(0, filename.length() - 3) + ".out";
        try (
                // 1. open files
                InputStream in = new GZIPInputStream(new BufferedInputStream(new FileInputStream(filename)));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(destFilename));
            ) {
            // 2. copy file
            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw ex;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new HelloException(2, filename);
        }
        // 3. auto close file
    }

    /**
     * Запись в текстовый файл
     * @param file файл для записи
     */
    public void writeToTextFile(File file) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.writeToTextFile()");
        // 1. open file for write
        try (Writer wr = new FileWriter(file);) {
            // 2. write text
            char[] bufer = "Hello World!\n".toCharArray();
            wr.write(bufer);
            wr.write("Hello from Java!\n");
            wr.write("Привет мир!\n");
            wr.write("Привет из Java!\n");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        // 3. auto close file
    }

    /**
     * Запись в текстовый файл
     * @param file файл для записи
     */
    public void writeToTextFile2(File file) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.writeToTextFile2()");
        // 1. open file for write
        try (PrintWriter wr = new PrintWriter(new FileWriter(file, Charset.forName(CHARSET_WIN), true));) {
            // 2. write text
            wr.println("Hello World!");
            wr.println("Hello from Java!");
            wr.println("Привет мир!");
            wr.println("Привет из Java!");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        // 3. auto close file
    }

    /**
     * Прочитать из текстового файла
     * @param file файл для чтения
     */
    public void readFromTextFile(File file) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.readFromTextFile()");
        // 1. open file for read
        try (Reader rd = new FileReader(file);) {
            // 2. read from file
            int c; // read char
            while ((c = rd.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        // 3. auto close file
    }
    
    /**
     * Прочитать из текстового файла
     * @param file файл для чтения
     */
    public void readFromTextFile2(File file) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.readFromTextFile2()");
        // 1. open file for read
        try (BufferedReader rd = new BufferedReader(new FileReader(file, Charset.forName(CHARSET_WIN)));) {
            // 2. read from file
            String line; // read line
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        // 3. auto close file
    }

    /**
     * Запись объекта в файл (сериализация)
     * @param file файд для записи
     * @param obj объект для записи
     */
    public void writeObjectToFile(File file, Object obj) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.writeObjectToFile()");
        // 1. open file for write
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));) {
            // 2. write to file
            out.writeObject(obj);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        // 3. auto close file
    }
    
    /**
     * Чтение объекта из файла (десериализация)
     * @param file файд для чтения
     * @return прочитанный объект
     */
    public Object readObjectFromFile(File file) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.readObjectFromFile()");
        Object obj = null;
        // 1. open file for read
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));) {
            // 2. read from file
            obj = in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        // 3. auto close file
        return obj;
    }

    /**
     * Произвольный доступ к файлу
     * @param file файл 
     */
    private void randomAccessFile(File file) {
        System.out.println("ru.lionsoft.javase.hello.HelloFile.randomAccessFile()");
        // 1. open file
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw");) {
            // 2. seek offset 0x30
            raf.seek(0x30);
            // 3. read byte
            int b = raf.read();
            System.out.println("b = " + b + " (0x" + Integer.toHexString(b) + ")");
            // 4. seek offset 0x30
            raf.seek(0x30);
            // 5. write byte
            raf.write(0x5a);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        // 6. auto close file
    }
    
}
