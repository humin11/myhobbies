package models;

import java.util.*;
import javax.persistence.*;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.validation.Email;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;
import com.feth.play.module.pa.user.FirstLastNameIdentity;
import models.TokenAction.Type;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import play.db.ebean.Model;
import play.data.validation.*;
import play.data.format.*;

@Entity
@Table(name="users")
public class User extends Model implements Subject{
	
	@Id
	@GeneratedValue
	public Long id;
	
	@Constraints.Email
	public String email;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String password;
	
	public String permission;
	
	public Boolean isinit;
	
	public Boolean status;
	
	public String firstName;
	
	public String lastName;

	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date lastLogin;

	public boolean active;

	public boolean emailValidated;

	@ManyToMany
    @JsonIgnore
	public List<SecurityRole> roles;

	@OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
	public List<LinkedAccount> linkedAccounts;

	@ManyToMany
    @JsonIgnore
	public List<UserPermission> permissions;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date create_at;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date update_at;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date locked_at;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date unlocked_at;

    @OneToOne
    @JsonIgnore
    public TPerson person;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    public List<TContact> self_contacts;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    public List<TCircle> circles;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    public List<TContact> in_others_contacts;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    public List<TPost> posts;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    public List<TComment> comments;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    public List<TLike> likes;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    public List<TPhoto> photos;

	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);

    public static User authenticate(String email, String password) {
        return find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

    public static List<User> findPeopleInCircles(List<TCircle> circles){
        return find.where().in("in_others_contacts.circle_members.circle",circles).findList();
    }
    
    @Override
	public String getIdentifier()
	{
		return Long.toString(id);
	}

	@Override
	public List<? extends Role> getRoles() {
		return roles;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		return permissions;
	}

	public static boolean existsByAuthUserIdentity(
			final AuthUserIdentity identity) {
		final ExpressionList<User> exp;
		if (identity instanceof UsernamePasswordAuthUser) {
			exp = getUsernamePasswordAuthUserFind((UsernamePasswordAuthUser) identity);
		} else {
			exp = getAuthUserFind(identity);
		}
		return exp.findRowCount() > 0;
	}

	private static ExpressionList<User> getAuthUserFind(
			final AuthUserIdentity identity) {
		return find.where().eq("active", true)
				.eq("linkedAccounts.providerUserId", identity.getId())
				.eq("linkedAccounts.providerKey", identity.getProvider());
	}

	public static User findByAuthUserIdentity(final AuthUserIdentity identity) {
		if (identity == null) {
			return null;
		}
		if (identity instanceof UsernamePasswordAuthUser) {
			return findByUsernamePasswordIdentity((UsernamePasswordAuthUser) identity);
		} else {
			return getAuthUserFind(identity).findUnique();
		}
	}

	public static User findByUsernamePasswordIdentity(
			final UsernamePasswordAuthUser identity) {
		return getUsernamePasswordAuthUserFind(identity).findUnique();
	}

	private static ExpressionList<User> getUsernamePasswordAuthUserFind(
			final UsernamePasswordAuthUser identity) {
		return getEmailUserFind(identity.getEmail()).eq(
				"linkedAccounts.providerKey", identity.getProvider());
	}

	public void merge(final User otherUser) {
		for (final LinkedAccount acc : otherUser.linkedAccounts) {
			this.linkedAccounts.add(LinkedAccount.create(acc));
		}
		// do all other merging stuff here - like resources, etc.

		// deactivate the merged user that got added to this one
		otherUser.active = false;
		Ebean.save(Arrays.asList(new User[] { otherUser, this }));
	}

	public static User create(final AuthUser authUser) {
		final User user = new User();
		user.roles = Collections.singletonList(SecurityRole
				.findByRoleName(controllers.Application.USER_ROLE));
		// user.permissions = new ArrayList<UserPermission>();
		// user.permissions.add(UserPermission.findByValue("printers.edit"));
		user.active = true;
		user.lastLogin = new Date();
		user.linkedAccounts = Collections.singletonList(LinkedAccount
				.create(authUser));

		if (authUser instanceof EmailIdentity) {
			final EmailIdentity identity = (EmailIdentity) authUser;
			// Remember, even when getting them from FB & Co., emails should be
			// verified within the application as a security breach there might
			// break your security as well!
			user.email = identity.getEmail();
			user.emailValidated = false;
		}

		if (authUser instanceof NameIdentity) {
			final NameIdentity identity = (NameIdentity) authUser;
			final String name = identity.getName();
			if (name != null) {
				user.name = name;
			}
		}
		
		if (authUser instanceof FirstLastNameIdentity) {
		  final FirstLastNameIdentity identity = (FirstLastNameIdentity) authUser;
		  final String firstName = identity.getFirstName();
		  final String lastName = identity.getLastName();
		  if (firstName != null) {
		    user.firstName = firstName;
		  }
		  if (lastName != null) {
		    user.lastName = lastName;
		  }
		}

		user.save();
		user.saveManyToManyAssociations("roles");
		// user.saveManyToManyAssociations("permissions");
		return user;
	}

	public static void merge(final AuthUser oldUser, final AuthUser newUser) {
		User.findByAuthUserIdentity(oldUser).merge(
				User.findByAuthUserIdentity(newUser));
	}

	public Set<String> getProviders() {
		final Set<String> providerKeys = new HashSet<String>(
				linkedAccounts.size());
		for (final LinkedAccount acc : linkedAccounts) {
			providerKeys.add(acc.providerKey);
		}
		return providerKeys;
	}

	public static void addLinkedAccount(final AuthUser oldUser,
			final AuthUser newUser) {
		final User u = User.findByAuthUserIdentity(oldUser);
		u.linkedAccounts.add(LinkedAccount.create(newUser));
		u.save();
	}

	public static void setLastLoginDate(final AuthUser knownUser) {
		final User u = User.findByAuthUserIdentity(knownUser);
		u.lastLogin = new Date();
		u.save();
	}

	public static User findByEmail(final String email) {
		return getEmailUserFind(email).findUnique();
	}

	private static ExpressionList<User> getEmailUserFind(final String email) {
		return find.where().eq("active", true).eq("email", email);
	}

	public LinkedAccount getAccountByProvider(final String providerKey) {
		return LinkedAccount.findByProviderKey(this, providerKey);
	}

	public static void verify(final User unverified) {
		// You might want to wrap this into a transaction
		unverified.emailValidated = true;
		unverified.save();
		TokenAction.deleteByUser(unverified, Type.EMAIL_VERIFICATION);
	}

	public void changePassword(final UsernamePasswordAuthUser authUser,
			final boolean create) {
		LinkedAccount a = this.getAccountByProvider(authUser.getProvider());
		if (a == null) {
			if (create) {
				a = LinkedAccount.create(authUser);
				a.user = this;
			} else {
				throw new RuntimeException(
						"Account not enabled for password usage");
			}
		}
		a.providerUserId = authUser.getHashedPassword();
		a.save();
	}

	public void resetPassword(final UsernamePasswordAuthUser authUser,
			final boolean create) {
		// You might want to wrap this into a transaction
		this.changePassword(authUser, create);
		TokenAction.deleteByUser(this, Type.PASSWORD_RESET);
	}


}

