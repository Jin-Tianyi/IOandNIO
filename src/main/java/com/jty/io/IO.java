package com.jty.io;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

public class IO {

    public void FileIO() {
        File file = new File("f1");
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
    }

    /***
     * ByteArrayInputStream
     * 字段：
     * buf  由该流的创建者提供的 byte 数组。
     * count  比输入流缓冲区中最后一个有效字符的索引大一的索引。
     * mark 流中当前的标记位置。
     * pos  要从输入流缓冲区中读取的下一个字符的索引。
     * 构造方法：
     * ByteArrayInputStream(byte[] buf)
     * ByteArrayInputStream(byte[] buf, int offset, int length)
     * 主要方法：
     * available() 返回可从此输入流读取（或跳过）的剩余字节数。count-pos
     * read() 从此输入流中读取下一个数据字节。pos位置的字节
     * read(byte[] b, int off, int len) 从off位置开始将最多 len 个数据字节从此输入流读入 byte 数组。
     * readNBytes(byte[] b, int off, int len)  同上
     * readAllBytes() 读取所有字节
     * mark(int readAheadLimit) 设置流中的当前标记位置。
     * reset() 将缓冲区的位置重置为标记位置。 pos->mark
     * skip(long n) 从此输入流中跳过 n 个输入字节。pos+n
     * close() 关闭 ByteArrayInputStream 无效。
     * @throws IOException
     */

    @Test
    public void ByteArrayInputStreamIO() throws IOException {
        //使用utf-8编码转化为字节数组
        byte[] e = "hello world".getBytes("utf-8");
        byte[] c = "你好 世界".getBytes("utf-8");
        ByteArrayInputStream byteArrayInputStreamE = new ByteArrayInputStream(e);
        ByteArrayInputStream byteArrayInputStreamC = new ByteArrayInputStream(c);
        try {
            byte[] bytes = new byte[6];
            byteArrayInputStreamE.read(bytes, 0, 4);
            //使用utf-8解码得到abcd,解码英文字符串 'hell',英文一个字节一个字符
            System.out.println(new String(bytes, "utf-8"));

            for (int i = 0; i < byteArrayInputStreamE.available(); i++) {
                //读取下一个字节输出
                System.out.println(byteArrayInputStreamE.read());
            }
            //使用utf-8解码得到abcd,解码中文字符串 '你好'，中文三个字节一个字符
            byteArrayInputStreamC.read(bytes, 0, 6);
            System.out.println(new String(bytes, "utf-8"));
            for (int i = 0; i < byteArrayInputStreamE.available(); i++) {
                //输出每个字节
                System.out.println(byteArrayInputStreamC.read());
            }

        } finally {
            byteArrayInputStreamE.close();
            byteArrayInputStreamC.close();
        }
    }

    /**
     * 方法：
     * void	reset() 将此 ByteArrayOutputStream的 count字段重置为零，以便丢弃输出流中当前累积的所有输出。
     * int	size() 返回缓冲区的当前大小。
     * byte[]	toByteArray() 创建一个新分配的字节数组。
     * String	toString() 使用平台的默认字符集将缓冲区的内容转换为字符串解码字节。
     * String	toString​(String charsetName) 通过使用名为charset的字节解码将缓冲区的内容转换为字符串。
     * String	toString​(Charset charset) 通过使用指定的charset解码字节，将缓冲区的内容转换为字符串。
     * void	write​(byte[] b, int off, int len) 从偏移量为 off的指定字节数组写入 len字节到此 ByteArrayOutputStream 。
     * void	write​(int b) 将指定的字节写入此 ByteArrayOutputStream 。
     * void	writeBytes​(byte[] b) 将指定字节数组的完整内容写入此 ByteArrayOutputStream 。
     * void	writeTo​(OutputStream out) 将此 ByteArrayOutputStream的完整内容写入指定的输出流参数，就像通过使用 out.write(buf, 0, count)调用输出流的write方法 out.write(buf, 0, count) 。
     * <p>
     * 构造方法：
     * ByteArrayOutputStream() 创建一个新的 ByteArrayOutputStream 。
     * ByteArrayOutputStream​(int size) 创建一个新的 ByteArrayOutputStream ，具有指定大小的缓冲区容量（以字节为单位）。
     *
     * @throws IOException
     */
    @Test
    public void ByteArrayOutputStreamIO() throws IOException {
        //使用utf-8编码转化为字节数组
        byte[] e = "hello world".getBytes("utf-8");
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            //将上述字节数组写入输出流
            baos.write(e, 0, e.length);
            byte[] bytes = baos.toByteArray();
            //新建一个ByteArrayInputStream输入流以输出流中的字节数组为参数
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ) {
                //hello world
                System.out.println(new String(bais.readAllBytes(), "utf-8"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /***
     * FileInputStream
     *构造函数：
     * FileInputStream fis = new FileInputStream("p1.jpg") 文件位于根路径下
     * FileInputStream fis = new FileInputStream("C:\\Users\\tyjin\\IdeaProjects\\IOandNIO\\p1.jpg") 文件绝对路径
     * FileInputStream fis = new FileInputStream(FileDescriptor.in) FileDescriptor文件描述符类的实例充当底层机器特定结构的不透明句柄，表示打开文件，打开套接字或其他字节源或接收器。 文件描述符的主要实际用途是创建FileInputStream或FileOutputStream来包含它。
     * 我们无法创建一个有效的该对象，以及使用它的方法，通常只能使用其内部三个常量：
     * static FileDescriptor.err 标准错误流的句柄。
     * static FileDescriptor.in 标准输入流的句柄。键盘输入
     * static FileDescriptor.out 标准输出流的句柄。输出到屏幕
     *
     * 特有方法：
     * FileChannel	getChannel() 返回与此文件输出流关联的唯一FileChannel对象。
     * FileDescriptor	getFD() 返回与此流关联的文件描述符FileDescriptor。
     *
     * FileOutputStream​
     * 构造函数：
     * FileOutputStream​(File file) 输出流内容写入file文件
     * FileOutputStream​(FileDescriptor fdObj) 创建要写入指定文件描述符的文件输出流，该文件描述符表示与文件系统中实际文件的现有连接。
     * FileOutputStream​(File file, boolean append) 创建文件输出流以写入由指定的 File对象表示的文件,append=true则以追加的方式，而不覆盖。
     * FileOutputStream​(String name) 创建name文件，件位于根路径下
     * FileOutputStream​(String name, boolean append) 创建文件输出流以写入具有指定名称的文件。
     *
     * 特有方法：
     * FileChannel	getChannel() 返回与此文件输出流关联的唯一FileChannel对象。
     * FileDescriptor	getFD() 返回与此流关联的文件描述符FileDescriptor。
     * @throws IOException
     */
    @Test
    public void FileStreamIO() throws IOException {
        //使用try-with-resources语法糖结构
        try (FileInputStream fis = new FileInputStream("C:\\Users\\tyjin\\Pictures\\视频项目\\p1.jpg");
             FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\tyjin\\Pictures\\视频项目\\p2.jpg"))) {
            byte[] pic;
            if (fis.available() > 0) {
                //读取图片以字节数组保存
                pic = fis.readAllBytes();
                for (byte b :
                        pic) {
                    //内容写入到p2.jpg
                    fos.write(b);
                }
            }
            System.out.println();

            //获得FileChannel，该类后面再说
            FileChannel fc = fis.getChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * BufferedInputStream​
     * 字段：
     * byte[]	buf	存储数据的内部缓冲区数组。
     * count 索引1大于缓冲区中最后一个有效字节的索引。
     * marklimit 在后续调用 reset方法失败之前调用 mark方法后允许的最大 mark读。
     * markpos	调用最后一个 mark方法时 pos字段的值。
     * pos	缓冲区中的当前位置。
     * <p>
     * 构造方法：
     * BufferedInputStream​(InputStream in)	创建一个 BufferedInputStream并保存其参数，即输入流 in ，供以后使用。
     * BufferedInputStream​(InputStream in, int size) 创建具有指定缓冲区大小的 BufferedInputStream ，并保存其参数（输入流 in ）供以后使用。
     * <p>
     * BufferedOutputStream
     * 字段：
     * buf[] 存储数据的内部缓冲区。
     * count 缓冲区中的有效字节数。
     * <p>
     * 特有方法：
     * void	flush() 刷新此缓冲的输出流，将内容同步到目标输出流。
     * void	write​(byte[] b, int off, int len) 将从偏移量 off开始的指定字节数组中的 len字节写入此缓冲输出流。
     * void	write​(int b) 将指定的字节写入此缓冲的输出流。
     *
     * @throws IOException
     */
    @Test
    public void BufferedStreamIO() throws IOException {
        byte[] bs = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             BufferedOutputStream bos = new BufferedOutputStream(baos);) {
            byte[] b0 = "hello world".getBytes("utf-8");
            for (byte b :
                    b0) {
                bos.write(b);
            }
            bos.flush();
            //获取写入的字节数组
            bs = baos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try (
                //以输出流中的字节数组作为参数
                ByteArrayInputStream bais = new ByteArrayInputStream(bs);
                BufferedInputStream bis = new BufferedInputStream(bais);) {
            byte[] bytes = new byte[6];
            bis.read(bytes, 0, 4);
            //使用utf-8解码得到abcd,解码英文字符串 'hell',英文一个字节一个字符
            System.out.println(new String(bytes, "utf-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * DataInputStream
     * 直接定义的字符串转字节数组无法使用 readXXX()方法，只有DataOutputStream.writexxx()写入的才可以使用，
     * 原因是在DataOutputStream写入时规定了各种基本类型的字节数，而String.getBytes()获得的字节数组根据编码的不同字每种字符节数也不同
     * 而DataInputStream.readxxx()(通过<<位运算符)是DataOutputStream.writexxx()的逆过程，只能解析DataOutputStream.writexxx()写入的字节数组（通过>>>位运算符）
     * 而通过直接定义字节数组的方式则不会对字节进行位运算对高位低位补0补1以满足不同数据类型的字节数。
     * <<表示左移移，不分正负数，低位补0；
     * >>表示右移，如果该数为正，则高位补0，若为负数，则高位补1；
     * >>>表示无符号右移，也叫逻辑右移，即若该数为正，则高位补0，而若该数为负数，则右移后高位同样补0
     * 特有方法：
     * readXXX() 读取固定个数输入字节并返回 XXX类型值。
     * readUTF() 读取使用 modified UTF-8格式编码的字符串。
     * <p>
     * DataOutputStream
     * writeXXX​(Object v) 将 XXX类型数据写入基础输出流，为四个字节，高字节优先。
     * writeUTF​(String str) 使用 modified UTF-8编码以与机器无关的方式将字符串写入基础输出流。
     *
     * @throws IOException
     */
    @Test
    public void DataStreamIO() throws IOException {

        /*test 1*/
        byte[] e = new byte[]{(byte) '我'};
        //模拟DataOutputStream.writexxx()，将字符转化为两个字节，可以使用readxxx()方法
        //  byte[] e = new byte[]{(byte) ('我'>>>8& 255),(byte) ('我'>>>0& 255)};
        System.out.println("test 1:");
        try (ByteArrayInputStream bais = new ByteArrayInputStream(e);
             DataInputStream bis = new DataInputStream(bais);) {
            while (bais.available() > 0) {
                //打印每个字节观察和DataOutputStream写入的字节数组的区别-> 17
                System.out.println(bais.read());
            }
            //重置pos
            bis.reset();
            //异常不满足两个字节
            System.out.println(bis.readChar());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /*test 2*/
        byte[] e1 = "我".getBytes();
        System.out.println("test 2:");
        try (ByteArrayInputStream bais = new ByteArrayInputStream(e1);
             DataInputStream bis = new DataInputStream(bais);) {
            while (bais.available() > 0) {
                //打印每个字节观察和DataOutputStream写入的字节数组的区别-> 230 136 145
                System.out.println(bais.read());
            }
            //重置pos
            bis.reset();
            //乱码
            //System.out.println(bis.readChar());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /*test 3*/
        System.out.println("test 3:");
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream bos = new DataOutputStream(baos);
        ) {
            bos.writeChar('我');
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                 DataInputStream bis = new DataInputStream(bais);) {
                while (bais.available() > 0) {
                    //打印每个字节观察和自定义的字符串得到的字节数组的区别 -> 98 17
                    System.out.println(bais.read());
                }
                //重置pos
                bis.reset();
                // 我
                System.out.println(bis.readChar());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 字段：
     * byte[]	buf 推回缓冲区。
     * int	pos 回推缓冲区中的位置，将从中读取下一个字节。该退回缓冲区从后往前填充，即pos初始位置为buf数组的大小（不指定为1），每次退回执行buf[--pos]=(byte)value
     * 特有方法：
     * void	unread​(byte[] b) 通过将字节数组复制到回送缓冲区的前面来推回字节数组，缓冲区大小必须>=b.length。
     * void	unread​(byte[] b, int off, int len) 通过将其复制到回送缓冲区的前面来推回一部分字节数组,缓冲区大小必须>=len。
     * void	unread​(int b) 通过将字节复制到回送缓冲区的前面来推回一个字节。
     *
     * @throws IOException
     */
    @Test
    public void PushbackInputStreamIO() throws IOException {

        byte[] e = "1234:123412341234".getBytes("utf-8");
        try (ByteArrayInputStream bais = new ByteArrayInputStream(e);
             //退回一个字节
             PushbackInputStream pis = new PushbackInputStream(bais);
             //退回多个字节需指定缓冲区大小
             // PushbackInputStream pis = new PushbackInputStream(bais,4);
        ) {
            byte[] bytes = new byte[20];
           /* while (pis.available() > 0) {
                pis.read(bytes, 0, 4);

                //如果包含':'，则将本次读取4个字节回退到缓冲区
                if (new String(bytes, "utf-8").contains(":")) {
                    // ->:123
                    System.out.println(new String(bytes, "utf-8"));
                    pis.unread(bytes, 0, 4);
                    //跳出循环
                    break;
                }
                // ->1234
                System.out.println(new String(bytes, "utf-8"));
            }*/

            for (int i = 0; i < pis.available() && i < bytes.length; i++) {
                byte b = (byte) pis.read();
                //如果该字节时':'，则退回到缓冲区，下次再次读取出来
                if (Objects.equals((byte) ':', b)) {
                    pis.unread(b);
                    break;
                } else {
                    bytes[i] = b;
                }
            }
            // ->1234
            System.out.println(new String(bytes, "utf-8"));
            byte[] bytes1 = new byte[20];
            //:123412341234
            bytes1 = pis.readAllBytes();
            System.out.println(new String(bytes1, "utf-8"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * PrintStream
     * 特有方法：
     * append​(char c) 将指定的字符追加到此输出流。
     * PrintStream	append​(CharSequence csq) 将指定的字符序列追加到此输出流。
     * PrintStream	append​(CharSequence csq, int start, int end) 将指定字符序列的子序列追加到此输出流。
     * PrintStream	format​(String format, Object... args) 使用指定的格式字符串和参数将格式化字符串写入此输出流。
     * PrintStream	format​(Locale l, String format, Object... args) 使用指定的格式字符串和参数将格式化字符串写入此输出流。
     * print(xxx v) 将xxx类型的数据打印到原输入流的末尾
     * println(xxx x) 同print(xxx v),再追加一个换行符
     * PrintStream printf​(String format, Object... args) 使用指定的格式字符串和参数将格式化字符串写入此输出流的便捷方法。
     * PrintStream printf​(Locale l, String format, Object... args) 使用指定的格式字符串和参数将格式化字符串写入此输出流的便捷方法。
     * 构造方法：
     * PrintStream​(File file) 使用指定的文件创建没有自动行刷新的新打印流。
     * PrintStream​(File file, String csn) 使用指定的文件和字符集创建一个没有自动行刷新的新打印流。
     * PrintStream​(File file, Charset charset) 使用指定的文件和字符集创建一个没有自动行刷新的新打印流。
     * PrintStream​(OutputStream out) 创建新的打印流。
     * PrintStream​(OutputStream out, boolean autoFlush) 创建新的打印流。
     * PrintStream​(OutputStream out, boolean autoFlush, String encoding) 创建新的打印流。
     * PrintStream​(OutputStream out, boolean autoFlush, Charset charset) 创建一个新的打印流，具有指定的OutputStream，自动行刷新和字符集。
     * PrintStream​(String fileName) 使用指定的文件名创建没有自动行刷新的新打印流。
     * PrintStream​(String fileName, String csn) 使用指定的文件名和字符集创建一个没有自动行刷新的新打印流。
     * PrintStream​(String fileName, Charset charset) 使用指定的文件名和字符集创建一个没有自动行刷新的新打印流。
     */
    @Test
    public void PrintStreamIO() {
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //123\n,begin添加到目标输出流的开头
                PrintStream ps = new PrintStream(baos, true).format("----%s\n", "123").append("begin")) {
            ps.println(':');
            byte[] bytes = "hello world".getBytes("utf-8");
            //写入内容
            ps.write(bytes);
            ps.print('-');
            //追加123后换行
            ps.println("123");
            //使用格式化打印方式，"f------>%d"会构建一个Formatter格式化器
            ps.printf("f------>%d\n", 1);
            ps.print(new String(":end"));
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());) {
               /*
               ----123
                begin:
                hello world-123
                f------>1
                :end
                */
                System.out.println(new String(bais.readAllBytes(), "utf-8"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ObjectOutputStream
     * 特有方法：
     * readXXX() XXX表示某一基本类型
     * readObject() 读取一个对象
     * readUnshared() 读取非共享对象
     * ObjectOutputStream
     * 特有方法：
     * 只有支持java.io.Serializable接口的对象才能写入流
     * writeXXX() XXX表示某一基本类型
     * writeObject() 读取一个对象
     * writeUnshared​(Object obj) 将“非共享”对象写入ObjectOutputStream。     *
     * 与Data输入输出流类似，支持Object类型，支持序列化与反序列化，内部使用DataOutputStream、DataInputStream实现
     */
    @Test
    public void ObjectStreamIO() {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            //写入数据
            oos.writeInt(12345);
            oos.writeObject("Today");
            oos.writeObject(new Date());
            User user = new User("123", "xiaoming");
            oos.writeObject(user);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                //读取数据
                System.out.println(ois.readInt());
                System.out.println((String) ois.readObject());
                System.out.println((Date) ois.readObject());
                System.out.println((User) ois.readObject());
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * PipedInputStream
     * 字段：
     * protected byte[]	buffer 传入数据的循环缓冲区。
     * protected int	in 循环缓冲区中位置的索引，当从连接的管道输出流接收时，将存储下一个数据字节。
     * protected int	out 循环缓冲区中位置的索引，此管道输入流将读取下一个数据字节。
     * protected static int	PIPE_SIZE 管道的循环输入缓冲区的默认大小。
     * <p>
     * 特有方法：
     * int	available()
     * 返回可以在不阻塞的情况下从此输入流中读取的字节数。
     * void	connect​(PipedOutputStream src)
     * 使此管道输入流连接到管道输出流 src 。
     * <p>
     * PipedOutputStream
     * 字段：
     * PipedInputStream sink 连接输入流
     * void	connect​(PipedInputStream snk)
     * 将此管道输出流连接到接收器。
     * void	flush()
     * 刷新此输出流并强制写出任何缓冲的输出字节。
     * <p>
     * 操作执行后必须关闭流
     */
    @Test
    public void PipedStreamIO() {
        //定义输入流线程任务
        class PipedInputStreamThread implements Runnable {
            private PipedInputStream pis;

            public PipedInputStreamThread() {
                this.pis = new PipedInputStream();
            }

            public PipedInputStream getPipedInputStream() {
                return this.pis;
            }

            @Override
            public void run() {
                try {
                    //不能使用该方法，若是该线程先于输出流线程执行，则会不满足条件往后执行，关闭输入流
                    //if(pis.available()>0)
                    System.out.println(new String(pis.readAllBytes(), "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        pis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        //定义输入流线程任务
        class PipedOutputStreamThread implements Runnable {
            private PipedOutputStream pos;

            public PipedOutputStreamThread() {
                this.pos = new PipedOutputStream();
            }

            public PipedOutputStream getPipedOutputStream() {
                return this.pos;
            }

            @Override
            public void run() {
                try {
                    byte[] bytes = "hello world".getBytes("utf-8");
                    pos.write(bytes);
                    //刷新缓冲区唤醒输入流线程
                    pos.flush();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        pos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            //创建任务
            PipedOutputStreamThread outer = new PipedOutputStreamThread();
            PipedInputStreamThread inner = new PipedInputStreamThread();
            PipedOutputStream pos = outer.getPipedOutputStream();
            PipedInputStream pis = inner.getPipedInputStream();
            //连接管道
            pos.connect(pis);
            //创建线程
            Thread putter = new Thread(outer);
            Thread getter = new Thread(inner);
            putter.start();
            getter.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 该输入流仅提供
     * available()、close()、read()、read​(byte[] b, int off, int len)方法。
     *
     * @throws IOException
     */
    @Test
    public void SequenceInputStreamIO() throws IOException {
        //创建三个字节输入流
        byte[] b1 = "hello".getBytes("utf-8");
        byte[] b2 = " java".getBytes("utf-8");
        byte[] b3 = " world".getBytes("utf-8");
        try (ByteArrayInputStream bais1 = new ByteArrayInputStream(b1);
             ByteArrayInputStream bais2 = new ByteArrayInputStream(b2);
             ByteArrayInputStream bais3 = new ByteArrayInputStream(b3);
        ) {
            //创建一个动态数组，elements()可返回Enumeration对象
            Vector<InputStream> v = new Vector(3);
            v.add(bais1);
            v.add(bais2);
            v.add(bais3);
            try (SequenceInputStream sis = new SequenceInputStream(v.elements());) {
                byte[] bytes = sis.readAllBytes();
                //hello java world
                System.out.println(new String(bytes, "utf-8"));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 构造方法：
     * StringBufferInputStream​(String s)	创建字符串输入流以从指定的字符串中读取数据。
     * <p>
     * 方法：
     * int	available()	 返回可以在不阻塞的情况下从输入流中读取的字节数。
     * int	read()	 从此输入流中读取下一个数据字节。
     * int	read​(byte[] b, int off, int len)	 从此输入流 len最多 len字节的数据读入一个字节数组。
     * void	reset()	 重置输入流以开始从此输入流的底层缓冲区的第一个字符读取。
     * long	skip​(long n)	 从此输入流中跳过 n字节的输入。
     *
     * @throws IOException
     */
    @Test
    public void StringBufferInputStreamIO() throws IOException {
        try ( //“你好 世界”不能正确写入和读出
              StringBufferInputStream sis = new StringBufferInputStream("hello world");) {
            byte[] bytes = sis.readAllBytes();
            //hello world
            System.out.println(new String(bytes, "utf-8"));
        }
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        LineNumberReader lineNumberReader = null;
    }

    /**
     * CharArrayWriter
     * 构造方法:
     * CharArrayWriter() 创建一个新的CharArrayWriter。
     * CharArrayWriter​(int initialSize) 创建具有指定初始大小的新CharArrayWriter。
     * <p>
     * 特有方法：
     * CharArrayWriter	append​(char c)	 将指定的字符追加到此writer。
     * CharArrayWriter	append​(CharSequence csq) 将指定的字符序列追加到此writer。
     * CharArrayWriter	append​(CharSequence csq, int start, int end)	 将指定字符序列的子序列追加到此writer。
     * int	size() 返回缓冲区的当前大小。
     * char[]	toCharArray()	 返回输入数据的副本。
     * String	toString()	 将输入数据转换为字符串。
     * <p>
     * CharArrayReader
     * 构造方法：
     * CharArrayReader​(char[] buf)	 从指定的字符数组创建CharArrayReader。
     * CharArrayReader​(char[] buf, int offset, int length)	 从指定的字符数组创建CharArrayReader。
     * <p>
     * int	read()	 读一个字符。
     * int	read​(char[] b, int off, int len)	 将字符读入数组的一部分。
     * boolean	ready()	 判断此流是否可以读取。
     *
     * @throws IOException
     */
    @Test
    public void CharArrayIO() throws IOException {
        try (
                CharArrayWriter out = new CharArrayWriter(20);) {
            char[] chars = "Hello world".toCharArray();
            //写入内容
            out.write(chars, 0, chars.length);
            //输出流中的内容转化为字符串
            System.out.println(out.toString());
            try ( //使用输出流中写入的字符数组创建一个输入流
                  CharArrayReader in = new CharArrayReader(out.toCharArray())) {
                if (in.ready()) {
                    char[] tar = new char[20];
                    //读取输出流中的目标数组
                    in.read(tar);
                    System.out.println(new String(tar));
                }
            }
        }
    }

    /**
     * OutputStreamWriter
     * 构造方法：
     * OutputStreamWriter​(OutputStream out)	 创建使用默认字符编码的OutputStreamWriter。
     * OutputStreamWriter​(OutputStream out, String charsetName)	  创建使用指定charset的OutputStreamWriter。
     * OutputStreamWriter​(OutputStream out, Charset cs)	 创建使用给定charset的OutputStreamWriter。
     * OutputStreamWriter​(OutputStream out, CharsetEncoder enc)	 创建使用给定charset编码器的OutputStreamWriter。
     * <p>
     * 特有方法：
     * void	flush()	  刷新流。
     * String	getEncoding()	 返回此流使用的字符编码的名称。
     * void	write​(char[] cbuf, int off, int len)	 写一个字符数组的一部分。
     * void	write​(int c)	 写一个字符。
     * void	write​(String str, int off, int len) 写一个字符串的一部分。
     * Writer append(CharSequence csq, int start, int end) 将csq字符串序列追加到输出流，起始位置
     * Writer append(CharSequence csq) 将csq字符串序列追加到输出流，起始位置
     * String getEncoding()  获取编码
     * <p>
     * InputStreamReader
     * 构造方法：
     * InputStreamReader​(InputStream in)	创建一个使用默认字符集的InputStreamReader。
     * InputStreamReader​(InputStream in, String charsetName) 创建一个使用指定charset的InputStreamReader。
     * InputStreamReader​(InputStream in, Charset cs) 创建一个使用给定charset的InputStreamReader。
     * InputStreamReader​(InputStream in, CharsetDecoder dec) 创建一个使用给定charset解码器的InputStreamReader。
     * 特有方法：
     * String	getEncoding()	返回此流使用的字符编码的名称。
     * int	read()	 读一个字符。
     * int	read​(char[] cbuf, int offset, int length)	 将字符读入数组的一部分。
     * boolean	ready()	 判断此流是否可以读取。
     */
    @Test
    public void InputStreamWriterAndReaderIO() {
        try (
                //创建一个字节数组输出流
                //ByteArrayOutputStream baos = new ByteArrayOutputStream();
                FileOutputStream baos = new FileOutputStream("t2.text", true);
                //以uft-8编码将字符数组写入字节数组输出流
                OutputStreamWriter out = new OutputStreamWriter(baos, "utf-8");) {
            char[] chars = "Hello world".toCharArray();
            //写入内容，此时并没有将数据写入ByteArrayOutputStream
            out.write(chars, 0, chars.length);
            //刷新缓冲区，将数据写入ByteArrayOutputStream，否则内容不会被写入到字节输出流
            //不手动刷新的话，只有缓冲区被占满才会自动写入字节输出流。
            out.flush();
            //查看编码
            System.out.println(out.getEncoding());
            try (
                    //以字节数组输出流中字节数组创建一个字节数组输入流
                    //ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    FileInputStream bais = new FileInputStream("t2.text");
                    //以utf-8编码从字节数组输入流读取字符
                    InputStreamReader in = new InputStreamReader(bais, "utf-8")) {
                if (in.ready()) {
                    char[] chars1 = new char[20];
                    //读取内容
                    in.read(chars1);
                    //Hello world
                    System.out.println(new String(chars1));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * FileWriter
     * FileWriter​(File file)	 给 File写一个 FileWriter ，使用平台的 default charset
     * FileWriter​(FileDescriptor fd)  构造一个 FileWriter给出的文件描述符，使用该平台的 default charset 。
     * FileWriter​(File file, boolean append)	在给出要写入的 FileWriter下构造 File ，并使用平台的 default charset构造一个布尔值，指示是否附加写入的数据。
     * FileWriter​(File file, Charset charset) 构造一个FileWriter给予File编写和charset 。
     * FileWriter​(File file, Charset charset, boolean append)	 构造FileWriter给出File写入， charset和一个布尔值，指示是否附加写入的数据。
     * FileWriter​(String fileName)	 构造一个 FileWriter给出文件名，使用平台的 default charset
     * FileWriter​(String fileName, boolean append)	 使用平台的 default charset构造一个 FileWriter给定一个文件名和一个布尔值，指示是否附加写入的数据。
     * FileWriter​(String fileName, Charset charset) 构造一个FileWriter给出文件名和charset 。
     * FileWriter​(String fileName, Charset charset, boolean append) 构造一个FileWriter给定一个文件名， charset和一个布尔值，指示是否附加写入的数据。
     * FileReader
     * 构造方法：
     * FileReader​(File file) 使用平台 FileReader ，在 File读取时创建一个新的 FileReader 。
     * FileReader​(FileDescriptor fd) 使用平台 default charset创建一个新的 FileReader ，给定 FileDescriptor进行读取。
     * FileReader​(File file, Charset charset) 创建一个新的FileReader ，给出File读取和charset 。
     * FileReader​(String fileName)	 使用平台 default charset创建一个新的 FileReader ，给定要读取的文件的 名称 。
     * FileReader​(String fileName, Charset charset) 给定要读取的文件的名称和FileReader ，创建一个新的FileReader 。
     */
    @Test
    public void FileWriterAndReaderIO() {
        try (
                //以追加方方式写入，编码方式为utf-8
                FileWriter out = new FileWriter("t1.text", Charset.forName("utf-8"), true);) {
            char[] chars = "Hello world".toCharArray();
            //写入内容，此时并没有将数据写入ByteArrayOutputStream
            out.write(chars, 0, chars.length);
            //刷新缓冲区，将数据写入ByteArrayOutputStream，否则内容不会被写入到字节输出流
            //不手动刷新的话，只有缓冲区被占满才会自动写入字节输出流。
            // out.flush();
            //查看编码
            System.out.println(out.getEncoding());
            try (
                    //以utf-8编码从字节数组输入流读取字符
                    FileReader in = new FileReader("t1.text", Charset.forName("utf-8"))) {
                if (in.ready()) {
                    char[] chars1 = new char[20];
                    //读取内容
                    in.read(chars1);
                    //Hello world
                    System.out.println(new String(chars1));
                    StringWriter stringReader = null;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * RandomAccessFile
     * 构造方法：
     * RandomAccessFile​(File file, String mode)	 创建随机访问文件流，以便从File参数指定的文件中读取，也可以选择写入。
     * RandomAccessFile​(String name, String mode)	 创建随机访问文件流，以便从具有指定名称的文件进行读取，并可选择写入该文件。
     * <p>
     * 特有方法：
     * void	close()	 关闭此随机访问文件流并释放与该流关联的所有系统资源。
     * FileChannel	getChannel() 返回与此文件关联的唯一FileChannel对象。
     * FileDescriptor	getFD()	 返回与此流关联的opaque文件描述符对象。
     * long	getFilePointer() 返回此文件中的当前偏移量。
     * long	length() 返回此文件的长度。
     * int	read() 从该文件中读取一个字节的数据。
     * int	read​(byte[] b) 从此文件读取最多 b.length字节的数据到一个字节数组。
     * int	read​(byte[] b, int off, int len) 从此文件读取最多 len字节的数据到一个字节数组。
     * boolean	readBoolean()	 从此文件中读取 boolean 。
     * byte	readByte()	 从该文件中读取带符号的8位值。
     * char	readChar()	 从此文件中读取字符。
     * double	readDouble() 从此文件中读取 double 。
     * float	readFloat()	 从此文件中读取 float 。
     * void	readFully​(byte[] b) 从当前文件指针开始，将此文件中的 b.length字节读入字节数组。
     * void	readFully​(byte[] b, int off, int len) 从当前文件指针开始，将此文件中的 len字节精确读入字节数组。
     * int	readInt()	 从此文件中读取带符号的32位整数。
     * String	readLine()	 从此文件中读取下一行文本。
     * long	readLong()	 从此文件中读取带符号的64位整数。
     * short	readShort()	 从该文件中读取带符号的16位数字。
     * int	readUnsignedByte()	 从该文件中读取无符号的8位数。
     * int	readUnsignedShort()	 从此文件中读取无符号的16位数字。
     * String	readUTF()	 从此文件中读取字符串。
     * void	seek​(long pos)	  设置从此文件的开头开始测量的文件指针偏移量，在该位置进行下一次读取或写入操作。
     * void	setLength​(long newLength)	 设置此文件的长度。
     * int	skipBytes​(int n) 尝试跳过 n字节的输入，丢弃跳过的字节。
     * void	write​(byte[] b) 从当前文件指针开始，将指定字节数组中的 b.length字节写入此文件。
     * void	write​(byte[] b, int off, int len) 将从偏移量 off开始的指定字节数组中的 len个字节写入此文件。
     * void	write​(int b)	 将指定的字节写入此文件。
     * void	writeBoolean​(boolean v)  将 boolean写入文件作为单字节值。
     * void	writeByte​(int v)	 将 byte写入文件作为单字节值。
     * void	writeBytes​(String s)	 将字符串作为字节序列写入文件。
     * void	writeChar​(int v)	  将 char作为双字节值写入文件，高字节优先。
     * void	writeChars​(String s)	 将字符串作为字符序列写入文件。
     * void	writeDouble​(double v) 双参数传递给转换 long使用 doubleToLongBits方法在类 Double ，然后写入该 long值到该文件作为一个八字节的数量，高字节。
     * void	writeFloat​(float v)	 浮子参数的转换 int使用 floatToIntBits方法在类 Float ，然后写入该 int值到该文件作为一个四字节数量，高字节。
     * void	writeInt​(int v) 将 int写入文件为四个字节，高字节优先。
     * void	writeLong​(long v)	 将 long写入文件为8个字节，高字节优先。
     * void	writeShort​(int v)  将 short写入文件为两个字节，高字节优先。
     * void	writeUTF​(String str)	 使用 modified UTF-8编码以与机器无关的方式将字符串写入文件。
     */
    @Test
    public void RandomAccessFileIO() {
        try (
                //以读写方式打开t1.text文件
                RandomAccessFile rw = new RandomAccessFile("t1.text", "rw");) {
            byte[] bytes1 = new byte[20];
            //先执行写操作后无法读取数据
            rw.read(bytes1);
            System.out.println(new String(bytes1, "utf-8"));

            //跳过文件原有内容，在文件尾操作，否则会覆盖原来的内容
            rw.seek(rw.length());
            byte[] bytes = "Hello worlds".getBytes("utf-8");
            rw.write(bytes);
            rw.writeChar('s');
            rw.seek(12);
            System.out.println(rw.readChar());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void FileChannelNIO() {
        //test 1:写入、读取字符串
        try (FileOutputStream fos = new FileOutputStream("t1.text");
             FileChannel outChannel = fos.getChannel();) {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.put("hello world".getBytes());
            //切换到读模式
            buf.flip();
            //若有剩余字节则向文件中写入
            while (buf.hasRemaining()) {
                outChannel.write(buf);
            }
            buf.clear();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //对同一文件可通过同一通道操作，此处为验证输入流的文件通道
        try (FileInputStream fis = new FileInputStream("t1.text");
             FileChannel inChannel = fis.getChannel()) {

            ByteBuffer buf = ByteBuffer.allocate(1024);
            inChannel.read(buf);
            byte[] bytes = new byte[1024];
            //切换到读
            buf.flip();
            //读取数据
            buf.get(bytes, 0, buf.remaining());
            System.out.println(new String(bytes));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //test 2: 文件复制,直接缓冲区

        try (FileInputStream fis = new FileInputStream("p1.jpg");
             FileChannel inC = fis.getChannel();
             FileOutputStream fos = new FileOutputStream("p2.jpg");
             FileChannel outC = fos.getChannel()) {

            //获取直接缓冲区
            ByteBuffer buf = ByteBuffer.allocateDirect(1024);
            //数据读入缓冲区
            while (inC.read(buf) != -1) {
                //切换到读
                buf.flip();
                //向p2.jpg写入
                outC.write(buf);
                //清空缓冲区
                buf.clear();
            }
            //or
            //inC.transferTo(0, inC.size(),outC);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //test 3: 文件复制,直接缓冲区，此时在缓冲区上的操作相当于直接操作磁盘

        try (
                FileChannel inC = FileChannel.open(Path.of("p1.jpg"), StandardOpenOption.READ);

                //文件操作枚举常量StandardOpenOption
                FileChannel outC = FileChannel.open(Path.of("p3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.READ)) {

            //获取映射文件
            //FileChannel.MapMode文件映射模式的类型安全枚举。
            ByteBuffer inBuf = inC.map(FileChannel.MapMode.READ_ONLY, 0, inC.size());
            ByteBuffer outBuf = outC.map(FileChannel.MapMode.READ_WRITE, 0, inC.size());
            outBuf.put(inBuf);
           /* byte[] bytes = new byte[inBuf.limit()];
            //获取缓冲区内容
            inBuf.get(bytes);
            //写入直接输出缓冲流
            outBuf.put(bytes);*/
            //or
            //inC.transferTo(0, inC.size(),outC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
