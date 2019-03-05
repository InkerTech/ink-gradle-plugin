package io.inker.ink.gradle.utils


class FileUtils {
    static boolean isFileUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false
        }

        URL remoteUrl
        try {
            remoteUrl = new URL(url)
        } catch (MalformedURLException e) {
            remoteUrl = null
        }
        return remoteUrl != null
    }

    static File downloadFile(String fileUrl, String localDir) {
        if (localDir == null || localDir.isEmpty()) {
            localDir = "."
        }

        URL remoteUrl = new URL(fileUrl)
        String filename = remoteUrl.getPath()
        int pos = filename.lastIndexOf('/')
        if (pos > 0) {
            filename = filename.substring(pos + 1)
        }
        File localFile = createFile(localDir + File.separator + filename)

        remoteUrl.withInputStream { is->
            localFile.withOutputStream { os->
                new BufferedOutputStream(os) << is
            }
        }

        return localFile
    }

    static File createFile(String filename) {
        if (filename == null || filename.isEmpty()) {
            return null
        }

        File file = new File(filename)
        File parentFile = file.getParentFile()
        if (parentFile && !parentFile.exists()) {
            parentFile.mkdirs()
        }
        file.createNewFile()
        return file
    }

    static File getFile(String filename) {
        if (filename == null || filename.isEmpty()) {
            return null
        }

        File file = new File(filename)
        return file.exists() ? file : null
    }
}
