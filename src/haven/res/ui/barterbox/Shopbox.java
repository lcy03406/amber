package haven.res.ui.barterbox;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import haven.Button;
import haven.Coord;
import haven.GItem;
import haven.GOut;
import haven.GSprite;
import haven.GSprite.Owner;
import haven.Glob;
import haven.Indir;
import haven.Inventory;
import haven.ItemInfo;
import haven.ItemInfo.SpriteOwner;
import haven.Label;
import haven.Loading;
import haven.Message;
import haven.MessageBuf;
import haven.ResData;
import haven.Resource;
import haven.Resource.Image;
import haven.Resource.Pagina;
import haven.RichText;
import haven.Tex;
import haven.TexI;
import haven.Text;
import haven.TextEntry;
import haven.UI;
import haven.Utils;
import haven.WItem;
import haven.Widget;
import haven.res.lib.tspec.Spec;
import haven.res.ui.tt.q.qbuff.QBuff;
import haven.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

// ui/barterstand
public class Shopbox extends Widget implements SpriteOwner, Owner {
    public static final Text any = Text.render(Resource.getLocString(Resource.BUNDLE_LABEL, "Any"));
    public static final Text qlbl = Text.render(Resource.getLocString(Resource.BUNDLE_LABEL, "Quality:"));
    public static final Tex bg = Resource.loadtex("ui/shopbox");
    public static final Coord itemc = new Coord(5, 5);
    public static final Coord buyc = new Coord(5, 66);
    public static final Coord pricec = new Coord(200, 5);
    public static final Coord qualc = (new Coord(200, 5)).add(Inventory.invsq.sz()).add(40, 0);
    public static final Coord cbtnc = new Coord(200, 66);
    public static final Coord spipec = new Coord(85, 66);
    public static final Coord bpipec = new Coord(280, 66);
    public ResData res;
    public Spec price;
    public Text num;
    public int left;
    public int pnum;
    public int pq;
    private Text pnumt;
    private Text pqt;
    private GSprite spr;
    private Object[] info = new Object[0];
    private Text quality;
    private Button spipe;
    private Button bpipe;
    private Button bbtn;
    private Button cbtn;
    private TextEntry pnume;
    private TextEntry pqe;
    public final boolean admin;
    public final AttrCache<Tex> itemnum = new One(this);
    private List<ItemInfo> cinfo;
    private Tex longtip = null;
    private Tex pricetip = null;
    private Random rnd = null;

    public static Widget mkwidget(UI ui, Object... var1) {
        boolean var2 = ((Integer) var1[0]).intValue() != 0;
        return new Shopbox(var2);
    }

    public Shopbox(boolean var1) {
        super(bg.sz());
        if (this.admin = var1) {
            this.spipe = (Button)this.add(new Button(75, "Connect"), spipec);
            this.bpipe = (Button)this.add(new Button(75, "Connect"), bpipec);
            this.cbtn = (Button)this.add(new Button(75, "Change"), cbtnc);
            this.pnume = (TextEntry)this.adda(new TextEntry(30, ""), pricec.add(Inventory.invsq.sz()).add(5, 0), 0.0D, 1.0D);
            this.pnume.canactivate = true;
            this.pnume.dshow = true;
            this.adda(new Label("Quality:"), qualc.add(0, 0), 0.0D, 1.0D);
            this.pqe = (TextEntry)this.adda(new TextEntry(40, ""), qualc.add(40, 0), 0.0D, 1.0D);
            this.pqe.canactivate = true;
            this.pqe.dshow = true;
        }
    }

    public void draw(GOut g) {
        g.image(bg, Coord.z);
        ResData var2 = this.res;
        GOut var3;
        if (var2 != null) {
            label56:
            {
                var3 = g.reclip(itemc, Inventory.invsq.sz());
                var3.image(Inventory.invsq, Coord.z);
                GSprite var4 = this.spr;
                if (var4 == null) {
                    try {
                        var4 = this.spr = GSprite.create(this, (Resource) var2.res.get(), var2.sdt.clone());
                    } catch (Loading var7) {
                        var3.image(((Image) WItem.missing.layer(Resource.imgc)).tex(), Coord.z, Inventory.sqsz);
                        break label56;
                    }
                }

                var4.draw(var3);
                if (this.itemnum.get() != null) {
                    var3.aimage((Tex) this.itemnum.get(), Inventory.sqsz, 1.0D, 1.0D);
                }

                if (this.num != null) {
                    g.aimage(this.num.tex(), itemc.add(Inventory.invsq.sz()).add(5, 0), 0.0D, 2.3D);
                }

                if (quality != null) {
                    g.aimage(qlbl.tex(), itemc.add(Inventory.invsq.sz()).add(5, 0), 0.0D, 1.0D);
                    g.aimage(quality.tex(), itemc.add(Inventory.invsq.sz()).add(8 + qlbl.tex().sz().x, 0), 0.0D, 1.0D);
                }
            }
        }

        Spec var8 = this.price;
        if(var8 != null) {
            var3 = g.reclip(pricec, Inventory.invsq.sz());
            var3.image(Inventory.invsq, Coord.z);

            try {
                var8.spr().draw(var3);
            } catch (Loading var6) {
                var3.image(((Image)WItem.missing.layer(Resource.imgc)).tex(), Coord.z, Inventory.sqsz);
            }

            if(!this.admin && this.pnumt != null) {
                g.aimage(this.pnumt.tex(), pricec.add(Inventory.invsq.sz()), 0.0D, 1.0D);
            }

            if(!this.admin && this.pqt != null) {
                g.aimage(qlbl.tex(), qualc, 0.0D, 1.0D);
                g.aimage(this.pqt.tex(), qualc.add(qlbl.tex().sz().x + 4, 0), 0.0D, 1.0D);
            }
        }

        super.draw(g);
    }

    public List<ItemInfo> info() {
        if (this.cinfo == null) {
            this.cinfo = ItemInfo.buildinfo(this, this.info);
            QBuff qb = quality();
            if (qb != null)
                quality = Text.render((int) qb.q + "");
            savelog();
        }
        return this.cinfo;
    }

    private QBuff getQBuff(List<ItemInfo> infolist) {
        for (ItemInfo info : infolist) {
            if (info instanceof QBuff)
                return (QBuff) info;
        }
        return null;
    }

    private QBuff quality() {
        try {
            for (ItemInfo info : info()) {
                if (info instanceof ItemInfo.Contents)
                    return getQBuff(((ItemInfo.Contents) info).sub);
            }
            return getQBuff(info());
        } catch (Loading l) {
        }
        return null;
    }

    public Object tooltip(Coord var1, Widget var2) {
        ResData var3 = this.res;
        if (var1.isect(itemc, Inventory.sqsz) && var3 != null) {
            try {
                if (this.longtip == null) {
                    BufferedImage var4 = ItemInfo.longtip(this.info());
                    Pagina var5 = ((Resource) var3.res.get()).layer(Resource.pagina);
                    if (var5 != null) {
                        var4 = ItemInfo.catimgs(0, new BufferedImage[]{var4, RichText.render("\n" + var5.text, 200, new Object[0]).img});
                    }

                    this.longtip = new TexI(var4);
                }

                return this.longtip;
            } catch (Loading var6) {
                return "...";
            }
        } else if (var1.isect(pricec, Inventory.sqsz) && this.price != null) {
            try {
                if (this.pricetip == null) {
                    this.pricetip = this.price.longtip();
                }

                return this.pricetip;
            } catch (Loading var7) {
                return "...";
            }
        } else {
            return super.tooltip(var1, var2);
        }
    }

    @Deprecated
    public Glob glob() {
        return this.ui.sess.glob;
    }

    public Resource resource() {
        return (Resource) this.res.res.get();
    }

    public GSprite sprite() {
        if (this.spr == null) {
            throw new Loading("Still waiting for sprite to be constructed");
        } else {
            return this.spr;
        }
    }

    public Resource getres() {
        return (Resource) this.res.res.get();
    }

    public Random mkrandoom() {
        if (this.rnd == null) {
            this.rnd = new Random();
        }

        return this.rnd;
    }

    private static Integer parsenum(TextEntry var0) {
        try {
            return var0.buf.line.equals("") ? Integer.valueOf(0) : Integer.valueOf(Integer.parseInt(var0.buf.line));
        } catch (NumberFormatException var2) {
            return null;
        }
    }

    public boolean mousedown(Coord var1, int var2) {
        if (var2 == 3 && var1.isect(pricec, Inventory.sqsz) && this.price != null) {
            this.wdgmsg("pclear", new Object[0]);
            return true;
        } else {
            return super.mousedown(var1, var2);
        }
    }

    public void wdgmsg(Widget var1, String var2, Object... var3) {
        if(var1 == this.bbtn) {
            this.wdgmsg("buy", new Object[0]);
        } else if(var1 == this.spipe) {
            this.wdgmsg("spipe", new Object[0]);
        } else if(var1 == this.bpipe) {
            this.wdgmsg("bpipe", new Object[0]);
        } else if(var1 == this.cbtn) {
            this.wdgmsg("change", new Object[0]);
        } else if(var1 != this.pnume && var1 != this.pqe) {
            super.wdgmsg(var1, var2, var3);
        } else {
            this.wdgmsg("price", new Object[]{parsenum(this.pnume), parsenum(this.pqe)});
        }
    }

    private void updbtn() {
        boolean var1 = this.price != null && this.pnum > 0;
        if (var1 && this.bbtn == null) {
            this.bbtn = (Button) this.add(new Button(75, "Buy"), buyc);
        } else if (!var1 && this.bbtn != null) {
            this.bbtn.reqdestroy();
            this.bbtn = null;
        }

    }

    private static Text rnum(String var0, int var1) {
        return var1 < 1 ? null : Text.render(String.format(var0, new Object[]{Integer.valueOf(var1)}));
    }

    public void uimsg(String var1, Object... var2) {
        if (var1 == "res") {
            this.res = null;
            this.spr = null;
            if (var2.length > 0) {
                ResData var3 = new ResData(this.ui.sess.getres(((Integer) var2[0]).intValue()), Message.nil);
                if (var2.length > 1) {
                    var3.sdt = new MessageBuf((byte[]) ((byte[]) var2[1]));
                }

                this.res = var3;
            }
        } else if (var1 == "tt") {
            this.info = var2;
            this.cinfo = null;
            this.longtip = null;
        } else {
            int var7;
            if (var1 == "n") {
                var7 = ((Integer) var2[0]).intValue();
                this.left = var7;
                this.num = Text.render(String.format("%d Left", new Object[]{Integer.valueOf(var7)}));
            } else if (var1 == "price") {
                byte var8 = 0;
                if (var2[var8] == null) {
                    var7 = var8 + 1;
                    this.price = null;
                } else {
                    var7 = var8 + 1;
                    Indir<Resource> var4 = this.ui.sess.getres(((Integer) var2[var8]).intValue());
                    Object var5 = Message.nil;
                    if (var2[var7] instanceof byte[]) {
                        var5 = new MessageBuf((byte[]) ((byte[]) var2[var7++]));
                    }

                    Object var6 = null;
                    if (var2[var7] instanceof Object[]) {
                        for (var6 = new Object[0][]; var2[var7] instanceof Object[]; var6 = Utils.extend((Object[]) var6, var2[var7++])) {
                            ;
                        }
                    }

                    this.price = new Spec(new ResData(var4, (Message)var5), Spec.uictx(this.ui), (Object[])var6);
                }

                this.pricetip = null;
                this.pnum = ((Integer) var2[var7++]).intValue();
                this.pq = ((Integer) var2[var7++]).intValue();
                if (!this.admin) {
                    this.pnumt = rnum("×%d", this.pnum);
                    this.pqt = this.pq > 0 ? rnum("%d+", this.pq) : any;
                } else {
                    this.pnume.settext(this.pnum > 0 ? Integer.toString(this.pnum) : "");
                    this.pnume.commit();
                    this.pqe.settext(this.pq > 0 ? Integer.toString(this.pq) : "");
                    this.pqe.commit();
                }
                
                this.updbtn();
            } else {
                super.uimsg(var1, var2);
            }
        }
    }
    
    Object findInfo(Object[] infolist, int id, int index) {
    	for (Object one: infolist) {
    		if (one instanceof Object[]) {
    			Object[] info = (Object[]) one;
    			if (info.length > index && info[0] instanceof Integer && (Integer)info[0] == id) {
    				return info[index];
    			}
    		}
    	}
    	return null;
    }
    
    String findItemName(Object[] info) {
    	Object[] list = info;
    	Object c = findInfo(list, 6189, 1);
    	if (c != null && c instanceof Object[]) {
    		list = (Object[]) c;
    		return findItemName(list);
    	}
    	Object o = findInfo(list, 6188, 1);
    	if (o != null && o instanceof String) {
    		String name = (String) o;
        	//if (Character.isDigit(name.charAt(0))) {
        	//	name = name.substring(name.indexOf(' ')+1);
        	//}
        	return name;
        }
    	return null;
    }
    
    int findItemNum(Object[] info, int def) {
    	Object[] list = info;
    	Object c = findInfo(list, 6189, 1);
    	if (c != null && c instanceof Object[]) {
    		list = (Object[]) c;
    		return findItemNum(list, def);
    	}
    	Object o = findInfo(list, 6186, 1);
    	if (o != null && o instanceof Integer) {
    		return (Integer) o;
        }
    	return def;
    }
   
    int findItemQ(Object[] info, int def) {
    	Object[] list = info;
    	Object c = findInfo(list, 6189, 1);
    	if (c != null && c instanceof Object[]) {
    		list = (Object[]) c;
    		return findItemQ(list, def);
    	}
    	Object o = findInfo(info, 6185, 1);
    	if (o != null && o instanceof Float) {
    		return (int)(float)(Float) o;
        }
    	return def;
    }
    
    String findItemCoin(Object[] infolist) {
    	Object oname = findInfo(infolist, 6200, 1);
    	if (oname != null && oname instanceof String) {
    		String name = (String) oname;
        	return name;
        }
    	return "";
    }
    
    class ShopItem {
    	public String group;
    	public String res;
    	public String name;
    	public int q;
    	public int num;
    	public boolean coin;
    	
    	public void init(ResData res, List<ItemInfo> cinfo, Object[] info, int q, int num) {
    		this.group = res.res.get().groupname();
        	this.res = res.res.get().basename() + "#" + res.res.get().ver;
        	this.q = findItemQ(info, q);
        	if (group.equals("coins")) {
        		this.coin = true;
        		this.res = this.res.replace('-', ' ').split(" ")[0];
        		this.name = findItemCoin(info) + "@" + this.res;
            	this.num = findItemNum(info, num);
        	} else {
        		this.name = getItemName(cinfo);
	        	if (this.name == null)
	        		this.name = findItemName(info);
        		if (this.name == null)
        			this.name = res.res.get().locname();
        		if (this.name == null)
        			this.name = "Unknown";
            	this.num = num;
        	}
    	}
    }
    
    class ShopOffer {
    	public ShopItem sell = new ShopItem();
    	public ShopItem price = new ShopItem();
    	public int left;
    }
    
    private String getItemName(List<ItemInfo> infolist) {
    	String name = null;
    	if (infolist == null)
    		return name;
        for (ItemInfo info : infolist) {
            if (info instanceof ItemInfo.Name) {
            	ItemInfo.Name iname = (ItemInfo.Name) info;
            	name = iname.str.text;
            }
            if (info instanceof ItemInfo.Contents) {
            	ItemInfo.Contents icontents = (ItemInfo.Contents) info;
            	name = getItemName(icontents.sub);
            }
        }
    	//if (Character.isDigit(name.charAt(0))) {
    	//	name = name.substring(name.indexOf(' ')+1);
    	//}
        return name;
    }

    ShopOffer so = null;

    static String logpath = "barterlog";
    static String sumpath = "bartersummary";
    static String buyprefix = "buy with";
    static String sellprefix = "sell with";
    void savelog() {
        if (!Config.bartersave)
        	return;
    	if (so != null)
    		return;
    	if (res == null)
    		return;
    	so = new ShopOffer();
    	so.sell.init(this.res, this.cinfo, this.info, 0, 1);
    	if (this.price != null) {
    		so.price.init(this.price.res, this.price.info(), this.price.info, this.pq, this.pnum);
    	}
    	so.left = this.left;
        String summary = String.format("[Barter Stand] Sell:%s q:%d num:%d left:%d Price:%s pq:%d pnum:%d",
        		so.sell.name, so.sell.q, so.sell.num, so.left, so.price.name, so.price.q, so.price.num);
        this.gameui().msg(summary);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String way, coinname;
        String data;
        if (!so.sell.coin && so.price.coin) {
        	way = sellprefix;
        	ShopItem item = so.sell;
        	ShopItem coin = so.price;
        	coinname = coin.name;
        	data = String.format("%s,%s,%d,%d,%d,%d", item.name,item.res,item.q,item.num,coin.num,so.left);
        } else if (so.sell.coin && !so.price.coin) {
        	way = buyprefix;
        	ShopItem item = so.price;
        	ShopItem coin = so.sell;
        	coinname = coin.name;
        	data = String.format("%s,%s,%d,%d,%d,%d", item.name,item.res,item.q,item.num,coin.num,so.left);
        //} else if (so.sell.coin && so.price.coin){
        //	way = "bank";
        //	coinname = "";
        //	ShopItem item = so.sell;
        //	ShopItem coin = so.price;
        //	data = String.format("%s,%s,%d,%s,%s,%d,%d", item.name,item.res,item.num,coin.name,coin.res,coin.num,so.left);
        } else {
        	return;
        }
        String path = String.format("%s/%s", logpath, date);
        new File(path).mkdirs();
        String filename = String.format("%s/%s %s.csv", path, way, coinname);
        try(FileOutputStream file = new FileOutputStream(filename, true);
        		OutputStreamWriter osw = new OutputStreamWriter(file, Charset.forName("UTF-8").newEncoder());
        		PrintWriter barterlog = new PrintWriter(osw, true);) {
            barterlog.println(data);
            barterlog.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static class ShopData {
    	public int q;
    	public float price;
    	public int left;
    	
    	public void update(int q, float price, int left) {
    		this.q = q;
    		this.price = price;
    		this.left = left;
    	}
    }
    
    static class ShopStat {
    	ShopData highQ = new ShopData();
    	ShopData lowQ = new ShopData();
    	ShopData highPrice = new ShopData();
    	ShopData lowPrice = new ShopData();
    	ShopData highLeft = new ShopData();
    }
    
    static void summarize(List<String> csvdata, File sumfile) {
    	boolean buy = sumfile.getName().startsWith(buyprefix);
    	TreeMap<String, ShopStat> data = new TreeMap<String, ShopStat>();
    	for (String csv : csvdata) {
    		String[] fields = csv.split(",");
    		String name = fields[0];
    		String res = fields[1];
    		int q = Integer.parseInt(fields[2]);
    		int num = Integer.parseInt(fields[3]);
    		float price = Integer.parseInt(fields[4])/(float)num;
    		int left = Integer.parseInt(fields[5]) * num;
    		//String title = String.format("%s@%s", name, res);
    		String title = name;
    		//TODO translate
    		ShopStat stat = data.get(title);
    		if (stat == null) {
    			stat = new ShopStat();
    			data.put(title, stat);
    		}
    		if (buy ? (q > stat.highQ.q || q == stat.highQ.q && price > stat.highQ.price)
    				: (q > stat.highQ.q || q == stat.highQ.q && price < stat.highQ.price)) {
    			stat.highQ.update(q, price, left);
    		}
    		if (stat.lowQ.left == 0 || (buy ? (q < stat.lowQ.q || q == stat.lowQ.q && price > stat.lowQ.price)
    				: (q < stat.lowQ.q || q == stat.lowQ.q && price < stat.lowQ.price))) {
    			stat.lowQ.update(q, price, left);
    		}
    		if (buy ? (price > stat.highPrice.price || price == stat.highPrice.price && q < stat.highPrice.q)
    				: (price > stat.highPrice.price || price == stat.highPrice.price && q > stat.highPrice.q)) {
    			stat.highPrice.update(q, price, left);
    		}
    		if (stat.lowPrice.left == 0 || (buy ? (price < stat.lowPrice.price || price == stat.lowPrice.price && q < stat.highPrice.q)
    				: (price < stat.lowPrice.price || price == stat.lowPrice.price && q > stat.highPrice.q))) {
    			stat.lowPrice.update(q, price, left);
    		}
    		if (buy ? (left > stat.highLeft.left || left == stat.highLeft.left && price > stat.highLeft.price)
    				: (left > stat.highLeft.left || left == stat.highLeft.left && price < stat.highLeft.price)) {
    			stat.highLeft.update(q, price, left);
    		}
    	}
        try(PrintWriter writer = new PrintWriter(sumfile)) {
        	if (buy) {
        		writer.println("物品,最高q,最高q的最高价,最低q,最低q的最高价,最高价,最高价的最低q,最低价,最低价的最低q,最大单笔,最大单笔的价格,最大单笔的q");
        	} else {
        		writer.println("物品,最高q,最高q的最低价,最低q,最低q的最低价,最高价,最高价的最高q,最低价,最低价的最高q,最大单笔,最大单笔的价格,最大单笔的q");
        	}
        	data.forEach((String title, ShopStat stat) -> {
        		writer.println(String.format("%s,%d,%.02f,%d,%.02f,%.02f,%d,%.02f,%d,%d,%.02f,%d",
        				title, stat.highQ.q, stat.highQ.price, stat.lowQ.q, stat.lowQ.price,
        				stat.highPrice.price, stat.highPrice.q, stat.lowPrice.price, stat.lowPrice.q,
        				stat.highLeft.left, stat.highLeft.price, stat.highLeft.q));
        	});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void summarize() {
    	File logdir = new File(logpath);
    	if (logdir.exists() && logdir.isDirectory()) {
    		File[] logdates = logdir.listFiles();
    		for (File logdate : logdates) {
    			if (logdate.isDirectory()) {
    				File[] logfiles = logdate.listFiles();
    				String sumdate = String.format("%s/%s", sumpath, logdate.getName());
    				File sumdir = new File(sumdate);
    				sumdir.mkdirs();
    				for (File logfile : logfiles) {
    					String logname = logfile.getName();
    					if (logname.endsWith(".csv")) {
    						try {
		    					List<String> data = Files.readAllLines(logfile.toPath());
								File sumfile = new File(String.format("%s/%s", sumdate, logname));
								summarize(data, sumfile);
    						} catch (IOException e) {
    				            e.printStackTrace();
    				        }
    					}
    				}
    			}
    		}
    	}
    }

    @Override
    public <C> C context(Class<C> var1) {
        return Spec.uictx.context(var1, this.ui);
    }

    public abstract class AttrCache<T> {
        private List<ItemInfo> forinfo;
        private T save;

        public AttrCache(Shopbox var1) {
            this.forinfo = null;
            this.save = null;
        }

        public T get() {
            try {
                List<ItemInfo> var1 = info();
                if (var1 != this.forinfo) {
                    this.save = find(var1);
                    this.forinfo = var1;
                }
            } catch (Loading var2) {
                return null;
            }

            return this.save;
        }

        protected abstract T find(List<ItemInfo> var1);
    }


    class One extends AttrCache<Tex> {
        One(Shopbox var1) {
            super(var1);
        }

        protected Tex find(List<ItemInfo> var1) {
            GItem.NumberInfo var2 = ItemInfo.find(GItem.NumberInfo.class, var1);
            return var2 == null ? null : new TexI(Utils.outline2(Text.render(Integer.toString(var2.itemnum()), Color.WHITE).img, Utils.contrast(Color.WHITE)));
        }
    }
}
