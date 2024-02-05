package com.example.androidtp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


class CardActivity : AppCompatActivity() {
    public var qrcode :String = "a"
    var token:String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        token = intent.getStringExtra("token")

    }

    public fun addCard(view: View)
    {

        val name = findViewById<EditText>(R.id.cardName)
        val type = 0
        readQR(view)
        val registerCard = Card(name.text.toString(), qrcode, type)
        Api().post<Card>("https://esicards.lesmoulinsdudev.com/cards", registerCard, ::addCardSuccess, securityToken = token.toString() )

    }




    public fun addCardSuccess(responseCode : Int)
    {
        if (responseCode == 200) {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)

        }
        else if (responseCode == 403){
            print("Non valid token")
            val intent2 = Intent(this, LoginActivity::class.java)
            startActivity(intent2)
        }
        else{
            print("error")
            val intent3 = Intent(this, RegisterActivity::class.java)
            startActivity(intent3)
        }
    }

     public fun readQR(view: View) {
         //Définit les options du scanner de code barre
         val options = GmsBarcodeScannerOptions.Builder().setBarcodeFormats(
             Barcode.FORMAT_QR_CODE,
             Barcode.FORMAT_AZTEC,).build()

         //Démarre une activité pour scanner un code barre
         val scanner = GmsBarcodeScanning.getClient(this,options)
         //Démarre le scan et définit les traitements à réaliser en fonction du résultat
         scanner.startScan()
             .addOnSuccessListener { barcode ->
                 qrcode = barcode.rawValue.toString()

             }
             .addOnCanceledListener {
                 //Traitement à réaliser si le scan du code barre a été annulé
                 print("canceled")
             }
             .addOnFailureListener { error ->
                 //Traitement à réaliser si une erreur s’est produite durant le scan
                 print("error")

             }
    }
































    /* public fun deleteCard(view: View) {
        val spinner = findViewById<Spinner>(R.id.cardsRecipe)
        val selectedCard = spinner.selectedItem?.toString()

        if (selectedCard != null) {
            // Cherchez la carte correspondante dans la liste des cartes chargées
            val cardToDelete = loadedCards?.find { it.name == selectedCard }

            if (cardToDelete != null) {
                val path = "https://esicards.lesmoulinsdudev.com/cards/${cardToDelete.id}")
                Api().delete(path, ::onSuccessDelete, securityToken = token.toString())
            }
        }
    }


    fun onSuccessDelete(responseCode: Int) {
        if (responseCode == 200) {
            println("La carte a été supprimée avec succès")
            loadCards()
        } else if (responseCode == 403) {
            println("Accès Refusé : $responseCode")
        } else if (responseCode == 404) {
            println("Carte non trouvée")
        } else {
            print("Une erreur s’est produite au niveau du serveur")
        }
    }

    */




}