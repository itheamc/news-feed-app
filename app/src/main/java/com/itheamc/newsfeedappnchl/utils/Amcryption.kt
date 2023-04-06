package com.itheamc.newsfeedappnchl.utils

import android.os.Build
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

object Amcryption {

    fun encrypt(value: String): String {
        // Generate a random key
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256, SecureRandom())
        val secretKey = keyGenerator.generateKey()

        // Encrypt the password
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedPassword = cipher.doFinal(value.toByteArray())

        // Encode the encrypted password and key as Base64 strings
        val encodedPassword = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(encryptedPassword)
        } else {
            android.util.Base64.encodeToString(encryptedPassword, android.util.Base64.DEFAULT)
        }


        val encodedKey = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(secretKey.encoded)
        } else {
            android.util.Base64.encodeToString(secretKey.encoded, android.util.Base64.DEFAULT)
        }

        // Return the encrypted password and key as a concatenated string
        return "$encodedPassword:$encodedKey"
    }


    fun decrypt(encrypted: String): String {
        // Split the encrypted password and key
        val parts = encrypted.split(":")
        val encodedPassword = parts[0]
        val encodedKey = parts[1]

        // Decode the encrypted password and key from Base64
        val decodedPassword = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(encodedPassword)
        } else {
            android.util.Base64.decode(encodedPassword, android.util.Base64.DEFAULT)
        }
        val decodedKey = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(encodedKey)
        } else {
            android.util.Base64.decode(encodedKey, android.util.Base64.DEFAULT)
        }

        // Decrypt the password
        val secretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedPassword = cipher.doFinal(decodedPassword)

        // Return the decrypted password as a string
        return String(decryptedPassword)
    }
}