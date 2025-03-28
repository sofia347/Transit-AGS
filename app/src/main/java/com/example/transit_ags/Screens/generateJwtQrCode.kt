package com.example.transit_ags.Utils

import android.graphics.Bitmap
import android.graphics.Color
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.util.Date

fun generateJwtQrCode(
    idUsuario: String,
    tipoUsuario: String,
    fechaPago: Long,
    status: String,
    size: Int = 512
): Bitmap {
    // Clave secreta segura
    val secret = "n5Kz8vH!B3rQsW1e@T9xA#LcZ7uYpM4d"
    val algorithm = Algorithm.HMAC256(secret)

    // Crear JWT con claims personalizados
    val token = JWT.create()
        .withClaim("id_usuario", idUsuario)
        .withClaim("tipo_usuario", tipoUsuario)
        .withClaim("fecha_pago", fechaPago)
        .withClaim("status", status)
        .withIssuedAt(Date())
        .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000)) // Expira en 30 min
        .sign(algorithm)

    // Generar QR del token
    val bitMatrix: BitMatrix = MultiFormatWriter().encode(token, BarcodeFormat.QR_CODE, size, size)
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

    for (x in 0 until size) {
        for (y in 0 until size) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }

    return bitmap
}
