package edu.ucucite.quizlettyn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;

public class ShowQrQzStActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr_qz_st);
        Intent intent = getIntent();
        String qzlty_code = intent.getStringExtra("qr_qzlty_code");
        getSupportActionBar().setTitle("Share quiz via qr code");
        ImageView imvQrCode = findViewById(R.id.qrcode_img);
        TextView qzlty_qr_info = findViewById(R.id.stmt_quiz_Qr);
        String qr_stmt_nfo = "Qr generated for " + intent.getStringExtra("qr_qzlty_name") + "\n" + qzlty_code;
        qzlty_qr_info.setText(qr_stmt_nfo);
        Bitmap bitmap = null;
        try {
            bitmap = textToImage(qzlty_code, 500, 500);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        imvQrCode.setImageBitmap(bitmap);

    }

    private Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,
                    width, height, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, width, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}