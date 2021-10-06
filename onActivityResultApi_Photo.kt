    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val img = ImageUtil.getPhotoByUri(requireActivity(), uri)
              // TO DO    We have bitmap photo from gallery
        }
    }
    
        private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                val bitmap = ImageUtil.getPhotoByUri(requireActivity(), uri)
               // TO DO    We have bitmap photo
            }
        }
    }
    
    
    private var latestTmpUri: Uri? = null
    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }
    
        private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png").apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }
        
        
        
   object ImageUtil {

    fun getPhotoByUri(context: Activity, selectedPhotoUri: Uri): Bitmap? {
        var loadedBitmap: Bitmap?
        if (Build.VERSION.SDK_INT < 28) {
            loadedBitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                selectedPhotoUri
            )
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, selectedPhotoUri)
            loadedBitmap = ImageDecoder.decodeBitmap(source)
        }
        if (loadedBitmap == null) return null
        val exif = ExifInterface(File(selectedPhotoUri.encodedPath!!))
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
        val matrix = Matrix()
        if (orientation == 6) {
            matrix.postRotate(90f)
        } else if (orientation == 3) {
            matrix.postRotate(180f)
        } else if (orientation == 8) {
            matrix.postRotate(270f)
        }
        loadedBitmap = Bitmap.createBitmap(
            loadedBitmap,
            0,
            0,
            loadedBitmap.width,
            loadedBitmap.height,
            matrix,
            true
        )
        return loadedBitmap
    }

}
