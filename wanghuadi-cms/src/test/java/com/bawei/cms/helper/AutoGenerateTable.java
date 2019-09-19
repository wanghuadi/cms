/**
 * 
 */
package com.bawei.cms.helper;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.wanghuadi.cms.domain.Article;
import com.wanghuadi.cms.domain.Category;
import com.wanghuadi.cms.domain.Channel;
import com.wanghuadi.cms.domain.Settings;
import com.wanghuadi.cms.domain.Slide;
import com.wanghuadi.cms.domain.User;
import com.wanghuadi.cms.metas.Gender;
import com.wanghuadi.cms.service.PassportService;

/**
 * 说明: 自动生成表，并且初始化配置和分类数据
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月16日 下午10:03:20
 */
public class AutoGenerateTable {

	/**
	 * 利用Hibernate自动创建表
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Configuration configuration = new Configuration().configure("auto_generate_table_config/hibernate.cfg.xml");
			Properties properties = configuration.getProperties();

			//设置属性：请在classpath下db.properties配置填写各自的数据库信息
			setVariableConfig(properties);

			StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
			SessionFactory sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);
			System.out.println("表创建结束\n\n开始初始化数据...");

			Session session = sessionFactory.openSession();

			//初始化配置
			initSettings(session);

			//初始化用户
			initUsers(session);

			//初始化频道和分类
			initChannelAndCategory(session);

			//初始化文章
			initArticles(session);
			
			//初始化幻灯片
			initSlide(session);

			session.close();
			sessionFactory.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 请在classpath下db.properties配置填写置以下3个值：
	 * jdbc.username = 数据库用户名
	 * jdbc.password = 数据库密码
	 * jdbc.jdbcUrl = 数据库连接
	 * @param configuration
	 */
	public static void setVariableConfig(Properties properties){
		InputStream inputStream = null;
		try {
			inputStream = AutoGenerateTable.class.getClassLoader().getResourceAsStream("db.properties");
			Properties configFileProperties = new Properties();
			configFileProperties.load(inputStream);
			if(configFileProperties.containsKey("jdbc.jdbcUrl")){
				properties.setProperty("hibernate.connection.url", configFileProperties.getProperty("jdbc.jdbcUrl"));
			}
			if(configFileProperties.containsKey("jdbc.username")){
				properties.setProperty("hibernate.connection.username", configFileProperties.getProperty("jdbc.username"));
			}
			if(configFileProperties.containsKey("jdbc.password")){
				properties.setProperty("hibernate.connection.password", configFileProperties.getProperty("jdbc.password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}



	/**
	 * 功能说明：初始化分类<br>
	 * @param session
	 * void
	 */
	public static void initSettings(Session session){
		Transaction transaction = null;
		try {
			Object data = session.get(Settings.class, 1);
			if(data != null){
				return;
			}
			transaction = session.getTransaction();
			transaction.begin();
			Settings settings = new Settings();
			settings.setSiteDomain("localhost");
			settings.setSiteName("CMS内容管理系统");
			settings.setArticleListSize(10);
			settings.setSlideSize(5);
			settings.setAdminUsername("admin");
			settings.setAdminPassword("888888");

			session.save(settings);

			transaction.commit();
			System.out.println("配置已初始化");
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	/**
	 * 功能说明：初始化一个测试用户，用户名：test，密码：123456<br>
	 * @param session
	 * void
	 */
	public static void initUsers(Session session){
		Transaction transaction = null;
		try {
			Query query = session.createQuery("select count(id) from com.bawei.cms.domain.User");
			Long amount = (Long) query.uniqueResult();
			if(amount > 0){
				return;
			}
			transaction = session.getTransaction();
			transaction.begin();

			String username = "test";
			String password = "123456";

			User user = new User();
			user.setNickname(username);
			user.setUsername(username);

			//加密密码
			password = PassportService.encrypt(username, password);
			user.setPassword(password);

			user.setGender(Gender.MALE);
			user.setLocked(false);
			user.setCreated(new Date());
			user.setUpdated(user.getCreated());

			session.save(user);
			transaction.commit();
			
			System.out.println("已初始化一个测试用户→用户名：" + username + "，密码：" + password);

		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	/**
	 * 功能说明：初始化分类<br>
	 * @param session
	 * void
	 */
	public static void initChannelAndCategory(Session session){
		Transaction transaction = null;
		try {
			Query query = session.createQuery("select count(id) from com.bawei.cms.domain.Channel");
			Long amount = (Long) query.uniqueResult();
			if(amount > 0){
				return;
			}
			transaction = session.getTransaction();
			transaction.begin();
			String datas[] = {
					"科技|互联网,软件,智能家居", 
					"财经|虚拟货币,股票,外汇,黄金,宏观经济",
					"国际|美国,亚洲,欧洲,非洲",
					"汽车|新车,SUV,汽车导购,用车",
					"体育|NBA,CBA,中超,意甲",
					"娱乐|电影,电视剧,综艺,明星八卦",
			"搞笑|段子,爆笑节目,童趣萌宠,雷人囧事"};
			for (int i = 0; i < datas.length; i++) {
				String data = datas[i];
				String cs[] = data.split("\\|");
				Channel channel = new Channel(null, cs[0], i);
				session.save(channel);
				String subCs[] = cs[1].split(",");
				for (int j = 0; j < subCs.length; j++) {
					String sub = subCs[j];
					Category category = new Category();
					category.setChannel(channel);
					category.setName(sub);
					category.setSorted(j);
					session.save(category);
				}
			}
			transaction.commit();
			System.out.println("频道和分类数据已初始化");
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	
	/**
	 * 功能说明：初始化两篇热门文章，一篇有图片，一篇没有图片<br>
	 * @param session
	 * void
	 */
	public static void initArticles(Session session){
		Transaction transaction = null;
		try {
			Query query = session.createQuery("select count(id) from com.bawei.cms.domain.Article");
			Long amount = (Long) query.uniqueResult();
			if(amount > 0){
				return;
			}

			query = session.createQuery("from com.bawei.cms.domain.User where username=?");
			query.setParameter(0, "test");
			User user = (User)query.uniqueResult();
			if(user == null){
				System.out.println("没有用户，初始化文章失败。");
				return;
			}
			
			query = session.createQuery("from com.bawei.cms.domain.Channel where name=?");
			query.setParameter(0, "科技");
			Channel channel = (Channel)query.uniqueResult();
			
			query = session.createQuery("from com.bawei.cms.domain.Category where name=?");
			query.setParameter(0, "互联网");
			Category category = (Category)query.uniqueResult();
			
			transaction = session.getTransaction();
			transaction.begin();


			Article article1 = new Article();
			article1.setTitle("惊人的大数据画像：你用什么手机，已透露了你的社会地位！");
			article1.setContent("<p>大数据，一直被认为是互联网公司的金矿，其多次利用潜力深不可测。所以基于市场调研或收集的相关数据，通过科学论断分析后，其显示出来的惊人画像有时准确的让人害怕。</p>"
					+ "<p>这不，腾讯旗下企鹅智库于2019年1月通过对网民进行调研，并联合腾讯新闻于4月发布最新2019年智能手机、智能硬件购买意愿报告，最终得出的用户画像是这样子的。/p>"
					+ "<p>先看小米：如果你是一二线城市用户，那选择小米手机的可能性会占据一半几率以上；而若再进一步，如果你处于一线城市，你选择小米的可能性要大于三星和华为这两大品牌。当然，从性别特点来说，男性用户选择小米的可能性更大，占比达到了61.8%。而在年龄分布方面，各阶段年龄在小米品牌中的分布比较均匀，其中又以20-39岁中青年用户占据主要地位，这可能与年轻人的冲劲及更追求极致体验有关。在月收入方面，3001-8000元档的用户占比最高，达38.4%。</p>"
					+ "<p>再看华为：在选择华为手机的用户中，有超过60.9%的用户是年龄超过30岁的用户；而在使用华为手机的用户中，三个人中就有一个是年龄在40岁及以上段。当然，若是按所处城市的级别来分析，偏爱华为的用户，生活在三四线的比一二线的更明显。而从受教育的学历方面对比，有超过一半的用户是初中及以下学历，这说明了一个什么问题呢？各人就见仁见智吧！在月收入方面，3001-8000元档的用户比例最高，达到了40.7%。不过跟小米相比感觉占比并没外界传言的那样明显，只2个左右的百分点差异并不像传言的“领导用华为、屌丝用小米”说法。</p>"
					+ "<p>最后看苹果：作为公认的最高端品牌定位，苹果iPhone的主力消费用户群体集中在20-39岁年龄段，总占比达到了67.9%，在19岁以下，苹果用户最少；如果考虑到学历分布，43.8%的用户在初中及以下，这是否说明土豪级别的相对比较低学历？另外从用户性别看，女性用户大大高于男性用户，可能iPhone作为一个面子问题，还是比较明显的。</p>"
					+ "<p>先就说这么三个品牌吧，其它手机用户群体的用户画像分析后边再来。</p>"
					);
			article1.setSummary("大数据，一直被认为是互联网公司的金矿，其多次利用潜力深不可测。所以基于市场调研或收集的相关数据，通过科学论断分析后，其显示出来的惊人画像有时准确的让人害怕。");
			article1.setPicture("/upload/2afd6aca2c4147d0b34c31184c67569d.jpg");
			article1.setAuthor(user);
			article1.setChannel(channel);
			article1.setCategory(category);
			
			article1.setHits(96);
			article1.setHot(true);
			article1.setStatus(1);
			article1.setDeleted(false);
			article1.setCreated(new Date());
			article1.setUpdated(article1.getCreated());
			session.save(article1);
			transaction.commit();
			System.out.println("已初始化1篇文章");
			
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}
	
	/**
	 * 功能说明：<br>
	 * @param session
	 * void
	 */
	public static void initSlide(Session session){
		Transaction transaction = null;
		try {
			Query query = session.createQuery("select count(id) from com.bawei.cms.domain.Slide");
			Long amount = (Long) query.uniqueResult();
			if(amount > 0){
				return;
			}

			transaction = session.getTransaction();
			transaction.begin();

			Slide slide1 = new Slide();
			slide1.setTitle("手工功能测试");
			slide1.setUrl("https://ke.qq.com/course/311128");
			slide1.setPicture("http://www.ctufo.com/wp-content/uploads/2016/06/26b1OOOPIC32.jpg");
			slide1.setCreated(new Date(System.currentTimeMillis() - 15 * 3600 * 1000));
			session.save(slide1);
			
			Slide slide2 = new Slide();
			slide2.setTitle("栗子网　写例子哥是认真的");
			slide2.setUrl("http://www.lizi.pw");
			slide2.setPicture("/upload/66e796c3g755eaef7edc8&690.jpg");
			slide2.setCreated(new Date());
			session.save(slide2);
			
			transaction.commit();
			
			System.out.println("已初始化2条广告幻灯片记录");
			
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}
}
