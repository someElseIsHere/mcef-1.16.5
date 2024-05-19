package com.cinemamod.mcef;

public final class MIMEUtil {
    public static String mimeFromExtension(String ext) {
        // TODO: might want to port https://github.com/CinemaMod/mcef/blob/master-1.19.2/src/main/resources/assets/mcef/mime.types
        //       adding cases to the switch isn't the most convenient thing
//        ext = ext.toLowerCase(Locale.US);
//        String ret = mimeTypeMap.get(ext);
//        if (ret != null)
//            return ret;

        // If the mimeTypeMap couldn't be loaded, fall back to common things
        final String result;
        switch (ext) {
            case "htm":
                result = "text/html";
                break;
            case "html":
                result = "text/html";
                break;
            case "css":
                result = "text/css";
                break;
            case "pdf":
                result = "application/pdf";
                break;
            case "xz":
                result = "application/x-xz";
                break;
            case "tar":
                result = "application/x-tar";
                break;
            case "cpio":
                result = "application/x-cpio";
                break;
            case "7z":
                result = "application/x-7z-compressed";
                break;
            case "zip":
                result = "application/zip";
                break;
            case "js":
                result = "text/javascript";
                break;
            case "json":
                result = "application/json";
                break;
            case "jsonml":
                result = "application/jsonml+json";
                break;
            case "jar":
                result = "application/java-archive";
                break;
            case "ser":
                result = "application/java-serialized-object";
                break;
            case "class":
                result = "application/java-vm";
                break;
            case "wad":
                result = "application/x-doom";
                break;
            case "png":
                result = "image/png";
                break;
            case "jpg":
                result = "image/jpeg";
                break;
            case "jpeg":
                result = "image/jpeg";
                break;
            case "gif":
                result = "image/gif";
                break;
            case "svg":
                result = "image/svg+xml";
                break;
            case "xml":
                result = "text/xml";
                break;
            case "txt":
                result = "text/plain";
                break;
            case "oga":
                result = "audio/ogg";
                break;
            case "ogg":
                result = "audio/ogg";
                break;
            case "spx":
                result = "audio/ogg";
                break;
            case "mp4":
                result = "video/mp4";
                break;
            case "mp4v":
                result = "video/mp4";
                break;
            case "mpg4":
                result = "video/mp4";
                break;
            case "m4a":
                result = "audio/mp4";
                break;
            case "mp4a":
                result = "audio/mp4";
                break;
            case "mid":
                result = "audio/midi";
                break;
            case "midi":
                result = "audio/midi";
                break;
            case "kar":
                result = "audio/midi";
                break;
            case "rmi":
                result = "audio/midi";
                break;
            case "mpga":
                result = "audio/mpeg";
                break;
            case "mp2":
                result = "audio/mpeg";
                break;
            case "mp2a":
                result = "audio/mpeg";
                break;
            case "mp3":
                result = "audio/mpeg";
                break;
            case "mp3a":
                result = "audio/mpeg";
                break;
            case "m2a":
                result = "audio/mpeg";
                break;
            case "mpeg":
                result = "video/mpeg";
                break;
            case "mpg":
                result = "video/mpeg";
                break;
            case "mpe":
                result = "video/mpeg";
                break;
            case "m1v":
                result = "video/mpeg";
                break;
            case "m2v":
                result = "video/mpeg";
                break;
            case "jpgv":
                result = "video/jpeg";
                break;
            case "h264":
                result = "video/h264";
                break;
            case "h261":
                result = "video/h261";
                break;
            case "h263":
                result = "video/h263";
                break;
            case "webm":
                result = "video/webm";
                break;
            case "flv":
                result = "video/flv";
                break;
            case "m4v":
                result = "video/m4v";
                break;
            case "qt":
                result = "video/quicktime";
                break;
            case "mov":
                result = "video/quicktime";
                break;
            case "ogv":
                result = "video/ogg";
                break;
            default:
                result = null;
                break;
        }

        return result;
    }
}
