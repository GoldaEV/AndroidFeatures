  binding.btnUpload.setOnClickListener {
            activityResultLauncher.launch(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        }
  
      private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            permissions.entries.forEach {
                val isGranted = it.value
                if (isGranted) {
                    getContent.launch("*/*")
                }
            }
        }
        
    private var fileUri: Uri? = null
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        fileUri = uri
        takeFile(fileUri)
    }
    
    
    var base64Str = ""
    private val supportedFormats = listOf("jpg", "png", "pdf", "doc", "xls")
    private fun takeFile(fileUri: Uri?) {
        fileUri?.let {
            val fileParams = dumpImageSize(it)
            if (fileParams.first > MAX_FILE_SIZE) {
                getString(R.string.tost_max_size_uploaded_file).toast(requireContext())
            } else if (fileParams.second.length > 4 && supportedFormats.contains(fileParams.second.substringAfterLast('.'))){
                base64Str = readTextFromUri(it)
            val title = getString(R.string.fragment_file_title_tv)
                binding.tvNameUploadFile.text = "$title${fileParams.second}"}
            else{
                getString(R.string.tost_unsuported_format).toast(requireContext())
            }
        }
    }
    
    
    @Throws(IOException::class)
    private fun readTextFromUri(uri: Uri): String {
        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return Base64.encodeToString(stringBuilder.toString().toByteArray(), Base64.DEFAULT)
    }
    
     private fun dumpImageSize(uri: Uri): Pair<Int, String> {
        var returnSize = 0
        var returnName = ""
        val cursor: Cursor? = contentResolver.query(
            uri, null, null, null, null, null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                val displayName: String =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                returnName = displayName
                Log.i(TAG, "Display Name: $displayName")
                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                val size: String = if (!it.isNull(sizeIndex)) {
                    it.getString(sizeIndex)
                } else {
                    "Unknown"
                }
                Log.i(TAG, "Display Size: $displayName")
                returnSize = size.toIntOrNull() ?: 0
            }
        }
        return Pair(returnSize, returnName)
    }


    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            permissions.entries.forEach {
                val isGranted = it.value
                if (isGranted) {
                    getContent.launch("*/*")
                }
            }
        }
