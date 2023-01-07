package tn.yassin.discovery.Dataa

public class Loginresponse {
    internal var token: String? = null
    internal var id: String? = null
    internal var name: String? = null
    internal var email: String? = null
    internal var password: String? = null
    internal var avatar: String? = null
    internal var bio: String? = null
    internal var msg: String? = null
    internal var role:String? = null
    // Getter token
    fun getToken(): String? {
        return token
    }
    // Setter token
    fun setToken(newToken: String?) {
        token = newToken
    }
    // Getter ID
    fun getID(): String? {
        return id
    }
    // Setter name
    fun setID(newID: String?) {
        id = newID
    }
    // Getter name
    fun getName(): String? {
        return name
    }
    // Setter name
    fun setName(newName: String?) {
        name = newName
    }
    // Getter email
    fun getEmail(): String? {
        return email
    }
    // Setter email
    fun setEmail(newEmail: String?) {
        email = newEmail
    }

    // Getter Password
    fun getPassword(): String? {
        return password
    }
    // Setter Password
    fun setPassword(newPassword: String?) {
        password = newPassword
    }
    // Getter avatar
    fun getAvatar(): String? {
        return avatar
    }
    // Setter avatar
    fun setAvatar(newAvatar: String?) {
        avatar = newAvatar
    }

    // Getter Bio
    fun getBio(): String? {
        return bio
    }
    // Setter Bio
    fun setBio(newBio: String?) {
        bio = newBio
    }

    // Getter msg
    fun getmsg(): String? {
        return msg
    }

    // Getter role
    fun getRole(): String? {
        return role
    }
    fun setRole(role: String?) {
        this.role = role
    }
}