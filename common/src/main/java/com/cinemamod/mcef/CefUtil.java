/*
 *     MCEF (Minecraft Chromium Embedded Framework)
 *     Copyright (C) 2023 CinemaMod Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 */

package com.cinemamod.mcef;

import net.minecraft.client.Minecraft;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class CefUtil {
    private CefUtil() {
    }

    private static boolean init;
    private static CefApp cefAppInstance;
    private static CefClient cefClientInstance;

    private static void setUnixExecutable(File file) {
        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);

        try {
            Files.setPosixFilePermissions(file.toPath(), perms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean init() {
        MCEFPlatform platform = MCEFPlatform.getPlatform();

        // Ensure binaries are executable
        if (platform.isLinux()) {
            File jcefHelperFile = new File(System.getProperty("mcef.libraries.path"), platform.getNormalizedName() + "/jcef_helper");
            setUnixExecutable(jcefHelperFile);
        } else if (platform.isMacOS()) {
            File mcefLibrariesPath = new File(System.getProperty("mcef.libraries.path"));
            File jcefHelperFile = new File(mcefLibrariesPath, platform.getNormalizedName() + "/jcef_app.app/Contents/Frameworks/jcef Helper.app/Contents/MacOS/jcef Helper");
            File jcefHelperGPUFile = new File(mcefLibrariesPath, platform.getNormalizedName() + "/jcef_app.app/Contents/Frameworks/jcef Helper (GPU).app/Contents/MacOS/jcef Helper (GPU)");
            File jcefHelperPluginFile = new File(mcefLibrariesPath, platform.getNormalizedName() + "/jcef_app.app/Contents/Frameworks/jcef Helper (Plugin).app/Contents/MacOS/jcef Helper (Plugin)");
            File jcefHelperRendererFile = new File(mcefLibrariesPath, platform.getNormalizedName() + "/jcef_app.app/Contents/Frameworks/jcef Helper (Renderer).app/Contents/MacOS/jcef Helper (Renderer)");
            setUnixExecutable(jcefHelperFile);
            setUnixExecutable(jcefHelperGPUFile);
            setUnixExecutable(jcefHelperPluginFile);
            setUnixExecutable(jcefHelperRendererFile);
        }

        String[] cefSwitches = new String[]{
                "--autoplay-policy=no-user-gesture-required",
                "--disable-web-security",
                "--enable-widevine-cdm" // https://canary.discord.com/channels/985588552735809696/992495232035868682/1151704612924039218
                // TODO: should probably make this configurable
                //       based off this page: https://magpcss.org/ceforum/viewtopic.php?f=6&t=11672
                //       it seems the solution to the white screen is to add the "--disable-gpu" switch
                //       but that shouldn't be done on all devices, so either we need to figure out a pattern and setup code to add the switch based off that, or add it as a config, if that is the case
        };

        if (!CefApp.startup(cefSwitches)) {
            return false;
        }

        MCEFSettings settings = MCEF.getSettings();

        CefSettings cefSettings = new CefSettings();
        cefSettings.windowless_rendering_enabled = true;
        cefSettings.background_color = cefSettings.new ColorType(0, 255, 255, 255);
        // Set the user agent if there's one defined in MCEFSettings
        if (!Objects.equals(settings.getUserAgent(), "null")) {
            cefSettings.user_agent = settings.getUserAgent();
        }

        cefAppInstance = CefApp.getInstance(cefSwitches, cefSettings);
        cefClientInstance = cefAppInstance.createClient();

        cefAppInstance.macOSTerminationRequestRunnable = new Runnable() {
            @Override
            public void run() {
                Minecraft.getInstance().stop();
            }
        };

        return init = true;
    }

    static void shutdown() {
        if (isInit()) {
            init = false;
            cefClientInstance.dispose();
            cefAppInstance.dispose();
        }
    }

    static boolean isInit() {
        return init;
    }

    static CefApp getCefApp() {
        return cefAppInstance;
    }

    static CefClient getCefClient() {
        return cefClientInstance;
    }

    public static String mimeFromExtension(String ext) {
        // TODO: might want to port https://github.com/CinemaMod/mcef/blob/master-1.19.2/src/main/resources/assets/mcef/mime.types
        //       adding cases to the switch isn't the most convenient thing
//        ext = ext.toLowerCase();
//        String ret = mimeTypeMap.get(ext);
//        if (ret != null)
//            return ret;

        //If the mimeTypeMap couldn't be loaded, fall back to common things
        switch (ext) {
            case "htm":
            case "html":
                return "text/html";

            case "css":
                return "text/css";

            case "pdf":
                return "application/pdf";

            case "xz":
                return "application/x-xz";
            case "tar":
                return "application/x-tar";
            case "cpio":
                return "application/x-cpio";
            case "7z":
                return "application/x-7z-compressed";
            case "zip":
                return "application/zip";

            case "js":
                return "text/javascript";
            case "json":
                return "application/json";
            case "jsonml":
                return "application/jsonml+json";

            case "jar":
                return "application/java-archive";
            case "ser":
                return "application/java-serialized-object";
            case "class":
                return "application/java-vm";

            case "wad":
                return "application/x-doom";

            case "png":
                return "image/png";

            case "jpg":
            case "jpeg":
                return "image/jpeg";

            case "gif":
                return "image/gif";

            case "svg":
                return "image/svg+xml";

            case "xml":
                return "text/xml";

            case "txt":
                return "text/plain";

            case "oga":
            case "ogg":
            case "spx":
                return "audio/ogg";

            case "mp4":
            case "mp4v":
            case "mpg4":
                return "video/mp4";

            case "m4a":
            case "mp4a":
                return "audio/mp4";

            case "mid":
            case "midi":
            case "kar":
            case "rmi":
                return "audio/midi";

            case "mpga":
            case "mp2":
            case "mp2a":
            case "mp3":
            case "mp3a":
            case "m2a":
                return "audio/mpeg";

            case "mpeg":
            case "mpg":
            case "mpe":
            case "m1v":
            case "m2v":
                return "video/mpeg";

            case "jpgv":
                return "video/jpeg";

            case "h264":
                return "video/h264";

            case "h261":
                return "video/h261";

            case "h263":
                return "video/h263";

            case "webm":
                return "video/webm";

            case "flv":
                return "video/flv";

            case "m4v":
                return "video/m4v";

            case "qt":
            case "mov":
                return "video/quicktime";

            case "ogv":
                return "video/ogg";

            default:
                return null;
        }
    }
}
