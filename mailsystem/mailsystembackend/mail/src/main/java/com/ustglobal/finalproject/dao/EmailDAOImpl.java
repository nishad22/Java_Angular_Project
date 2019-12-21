package com.ustglobal.finalproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ustglobal.finalproject.dto.EmailBean;
import com.ustglobal.finalproject.dto.UserBean;
@Repository
public class EmailDAOImpl implements IEmailDAO{

	@PersistenceUnit
	private EntityManagerFactory factory;
	private EntityManager manager = null;
	private EntityTransaction transaction = null;
	@Override
	public boolean registerUser(UserBean user) {
		// TODO Auto-generated method stub
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.persist(user);
			transaction.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
			return false;
		}

	}

	@Override
	public UserBean loginUser(String email, String password) {
		String jpql = "from UserBean where email=:email and password=:password";
		manager = factory.createEntityManager();
		TypedQuery<UserBean> query = manager.createQuery(jpql, UserBean.class);
		query.setParameter("email", email);
		query.setParameter("password",password);
		UserBean bean = query.getSingleResult();
		if(bean != null) {
			return bean;
		} else {
			return null;
		}
	}

	@Override
	public UserBean forgotPassowrd(String email, String question, String answer) {
		manager = factory.createEntityManager();
		String jpql="from UserBean where email=:email and securityQuestion=:question and answer=:answer ";
		try {
			TypedQuery<UserBean> query=manager.createQuery(jpql, UserBean.class);
			query.setParameter("email",email);
			query.setParameter("question",question);
			query.setParameter("answer",answer);

			UserBean dto = (UserBean)query.getSingleResult();
			if(dto != null) {
				return dto;
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public boolean changePassword(String email, String password,String newPassword) {
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();

		String jpql="from UserBean where email=:email and password=:password";
		try {
			//			TypedQuery<UserBean>query=manager.createQuery(jpql, UserBean.class);
			Query query =  manager.createQuery(jpql);
			query.setParameter("email",email);
			query.setParameter("password",password);
			UserBean dto = (UserBean)query.getSingleResult();
			if(dto != null) {
				transaction.begin();
				dto.setPassword(newPassword);
				manager.persist(dto);
				transaction.commit();
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean compose(String subject, String message, String email,int id) {
		// TODO Auto-generated method stub
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		String jpql1 = "from UserBean where email=:email";

		Query que1 = manager.createQuery(jpql1);
		que1.setParameter("email", email);
		UserBean user_to = (UserBean)que1.getSingleResult();

		UserBean user_from = manager.find(UserBean.class, id);

		try {
			transaction.begin();
			EmailBean bean = new EmailBean();
			bean.setMessage(message);
			bean.setSubject(subject);
			bean.setStatus("sent");

			bean.setUserbean(user_from);
			bean.setUser(user_to);
			manager.persist(bean);
			transaction.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<EmailBean> inbox(int to_id) {
		// TODO Auto-generated method stub
		String jpql = "from EmailBean where to_id=:to_id";
		manager = factory.createEntityManager();
		//		TypedQuery<EmailBean> email = manager.createQuery(jpql, EmailBean.class);
		try {
			TypedQuery<EmailBean> email = manager.createQuery(jpql, EmailBean.class);
			email.setParameter("to_id", to_id);
			List<EmailBean> list = (List<EmailBean>)email.getResultList();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		//       Query email = manager.createQuery(jpql);

	}

	@Override
	public List<EmailBean> send(int from_id) {
		// TODO Auto-generated method stub
		manager = factory.createEntityManager();
		String jpql = "from EmailBean where from_id=:from_id";
		TypedQuery<EmailBean> email = manager.createQuery(jpql, EmailBean.class);
		email.setParameter("from_id" ,from_id );
		List<EmailBean> list = email.getResultList();
		if(list!=null)
			return list;
		else 
			return null;

	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		try {
			EmailBean bean =manager.find(EmailBean.class,id);
			bean.setStatus("deleted");
			transaction.commit();
			return true;

		}catch(Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean draft(String subject, String message, String email, int id) {
		// TODO Auto-generated method stub
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		String jpql1 = "from UserBean where email=:email";

		Query que1 = manager.createQuery(jpql1);
		que1.setParameter("email", email);
		UserBean user_to = (UserBean)que1.getSingleResult();

		UserBean user_from = manager.find(UserBean.class, id);

		try {
			transaction.begin();
			EmailBean bean = new EmailBean();
			bean.setMessage(message);
			bean.setSubject(subject);
			bean.setStatus("draft");
			bean.setUserbean(user_from);
			bean.setUser(user_to);
			manager.persist(bean);
			transaction.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<EmailBean> draftlist(int to_id, String status) {
		// TODO Auto-generated method stub
		String jpql = "from EmailBean where to_id=:to_id and status=:status";
		manager = factory.createEntityManager();
		TypedQuery<EmailBean> bean = manager.createQuery(jpql,EmailBean.class);
		bean.setParameter("to_id", to_id);
		bean.setParameter("status", status);
		List<EmailBean> email = bean.getResultList();
		return email;
	}

	@Override
	public boolean deleteInbox(int id) {
		
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		try {
		transaction.begin();
		EmailBean bean=manager.find(EmailBean.class, id);
		manager.remove(bean);
		transaction.commit();
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		}
	}








































































}
